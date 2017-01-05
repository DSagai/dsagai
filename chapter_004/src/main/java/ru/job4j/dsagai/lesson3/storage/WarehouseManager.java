package ru.job4j.dsagai.lesson3.storage;

import ru.job4j.dsagai.lesson3.exceptions.StorageLimitExcess;
import ru.job4j.dsagai.lesson3.exceptions.UnsupportedStorageType;
import ru.job4j.dsagai.lesson3.food.Food;
import ru.job4j.dsagai.lesson3.util.ConfigReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


/**
 * Class WarehouseManager provides coordination
 * between several warehouses.
 * It decides where to store new food items in
 * dependence of allowed storage temperature and
 * storages plenum.
 * @author dsagai
 * @since 01.01.2017
 */
public class WarehouseManager implements Storage {
    private final Storage[] refrigeratorStorages;
    private final Storage[] coldStorages;
    private final Storage[] normalStorages;

    private final int refrigeratorTemp;
    private final int coldTemp;
    private final int normTemp;
    private final int warehouseCapacity;

    /**
     * default constructor.
     * @param refrigeratorCount int.
     * @param coldStorCount int.
     * @param normalStorCount int.
     */
    public WarehouseManager(int refrigeratorCount, int coldStorCount, int normalStorCount) {
        this.refrigeratorStorages = new Storage[refrigeratorCount];
        this.coldStorages = new Storage[coldStorCount];
        this.normalStorages = new Storage[normalStorCount];
        initStorageArray(this.refrigeratorStorages);
        initStorageArray(this.coldStorages);
        initStorageArray(this.normalStorages);

        ConfigReader configReader = ConfigReader.getInstance();
        this.normTemp = Integer.parseInt(configReader.getProperty("borderTemp.normal","0"));
        this.coldTemp = Integer.parseInt(configReader.getProperty("borderTemp.cold","0"));;
        this.refrigeratorTemp = Integer.parseInt(configReader.getProperty("borderTemp.refrigerator","0"));;
        this.warehouseCapacity = Integer.parseInt(configReader.getProperty("warehouseCapacity","1"));
    }




    private void initStorageArray(Storage[] storages) {
        for (int i = 0; i < storages.length; i++){
            storages[i] = new StorageImpl(this.warehouseCapacity);
        }
    }

    @Override
    /**
     * method adds new food item to the storage.
     * @param food Food.
     * @return boolean.
     * @throws StorageLimitExcess
     */
    public boolean add(Food food) throws Exception {

        return add(getStorageGroupByTemp(food), food);
    }

    private Storage[] getStorageGroupByTemp(Food food) throws UnsupportedStorageType {
        Storage[] result;
        if (food.getStoreTemp() <= this.refrigeratorTemp) {
            result = this.refrigeratorStorages;
        } else if (food.getStoreTemp() <= this.coldTemp) {
            result = this.coldStorages;
        } else if (food.getStoreTemp() <= this.normTemp) {
            result = this.normalStorages;
        } else {
            throw new UnsupportedStorageType(String.format("Can not store %s under condition %d degrees",food.getName(), food.getStoreTemp()));
        }
        return result;
    }

    private boolean add(Storage[] storages, Food food) throws Exception {
        boolean result = false;
        for (Storage storage : storages){
            if (!storage.isFull()){
                if (storage.add(food)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    /**
     * method provides group entry for food items.
     * @param foods Collection<Food>.
     * @return boolean.
     * @throws StorageLimitExcess
     */
    public boolean addAll(Collection<Food> foods) throws Exception {
        boolean result = false;
        for (Food food : foods) {
            result = add(food);
            if (!result) {
                break;
            }
        }
        return result;
    }

    @Override
    /**
     * removes food item from the storage.
     * @param food Food.
     * @return boolean.
     */
    public boolean remove(Food food) {
        boolean result = false;
        try {
            Storage[] storages = getStorageGroupByTemp(food);
            for (Storage storage : storages){
                result = storage.remove(food);
                if (result) {
                    break;
                }
            }
        } catch (UnsupportedStorageType e) {
            result = false;
        }
        return result;
    }

    @Override
    /**
     * Retrieves, but does not remove list of stored food items.
     * @return List<Food>.
     */
    public List<Food> getFoods() {
        List<Food> foods = new ArrayList<>();
        foods.addAll(getFoods(this.refrigeratorStorages));
        foods.addAll(getFoods(this.coldStorages));
        foods.addAll(getFoods(this.normalStorages));
        return foods;
    }

    /**
     * Retrieves food items from selected storage group.
     * @param storages Storage[].
     * @return List<Food>.
     */
    private List<Food> getFoods(Storage[] storages){
        List<Food> foods = new ArrayList<>();
        for (Storage storage : storages) {
            foods.addAll(storage.getFoods());
        }
        return foods;
    }

    @Override
    /**
     * Retrieves and removes list of stored food items.
     * @return List<Food>.
     */
    public List<Food> poolFoods() {
        List<Food> foods = new ArrayList<>();
        foods.addAll(poolFoods(this.refrigeratorStorages));
        foods.addAll(poolFoods(this.coldStorages));
        foods.addAll(poolFoods(this.normalStorages));
        return foods;
    }

    /**
     * pools food items from the selected storage group.
     * @param storages  Storage[].
     * @return List<Food>.
     */
    private List<Food> poolFoods(Storage[] storages){
        List<Food> foods = new ArrayList<>();
        for (Storage storage : storages){
            foods.addAll(storage.poolFoods());
        }
        return foods;
    }

    @Override
    /**
     * removes all items from the storage.
     */
    public void clear() {
        clear(this.refrigeratorStorages);
        clear(this.coldStorages);
        clear(this.normalStorages);
    }

    /**
     * Clears food items from selected storage group.
     * @param storages Storage[].
     */
    private void clear(Storage[] storages){
        for (Storage storage : storages){
            storage.clear();
        }
    }

    @Override
    /**
     * @return true when you excess storage limit
     */
    public boolean isFull() {
        return isFull(this.refrigeratorStorages) &&
                isFull(this.coldStorages) &&
                isFull(this.normalStorages);
    }

    /**
     * Checks isFull for the selected storage group.
      * @param storages Storage[].
     * @return boolean.
     */
    private boolean isFull(Storage[] storages) {
        boolean result = true;
        for (Storage storage : storages) {
            if (!storage.isFull()) {
                result = false;
                break;
            }
        }
        return result;
    }
}
