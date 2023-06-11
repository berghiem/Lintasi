package com.catostudioaruna.alphabet.db;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AlphabetViewModel extends AndroidViewModel {
    private AlphabetRepository repository;

    private LiveData<User> user;
    private LiveData<List<Sticker>> stickers;
    private LiveData<List<Character>> characters;
    private LiveData<List<SkillJoinCharacter>> skillCharacters;
    private LiveData<List<Vehicle>> vehicles;
    private LiveData<List<Animal>> animals;
    private LiveData<List<Fruit>> fruits;
    private LiveData<List<Huruf>> hurufs;



    public AlphabetViewModel(@NonNull Application application) {
        super(application);
        this.repository = new AlphabetRepository(application);
        this.user = repository.getUser();
        this.stickers = repository.getStickers();
        this.characters = repository.getCharacters();
        this.skillCharacters = repository.getAllSkillByCharacter();
        this.vehicles = repository.getAllVehicles();
        this.animals = repository.getAllAnimals();
        this.fruits = repository.getAllFruits();
        this.hurufs = repository.getAllHurufs();

    }

    public LiveData<User> getUser() {
        return user;
    }



    public LiveData<List<Sticker>> getStickers() {
        return stickers;
    }

    public LiveData<Sticker> getReward() {
        return repository.getReward();
    }


    public LiveData<List<Sticker>> getCollectedSticker() {
        return repository.getCollectedSticker();
    }

    public List<Sticker> getUncollectedSticker() {
        return repository.getUncollectedSticker();
    }

    public LiveData<List<Character>> getCharacters() {


        return repository.getCharacters();
    }

    public LiveData<Character> getCharacterById(int id) {


        return repository.getCharacterById(id);
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return vehicles;
    }

    public LiveData<List<Animal>> getAllAnimall() {
        return animals;
    }

    public LiveData<List<Integer>> getAllAnimallId() {
        return repository.getAllAnimalId();
    }

    public LiveData<Animal> getAnimalById(int id) {
        return repository.getAnimalById(id);
    }


    public LiveData<List<Fruit>> getAllFruit() {
        return fruits;
    }
    public LiveData<List<Integer>> getAllFruitId() {
        return repository.getAllFruitId();
    }

    public LiveData<Fruit> getFruitById(int id) {
        return repository.getFruitById(id);
    }



    public LiveData<List<Huruf>> getAllHuruf() {
        return hurufs;
    }

    public LiveData<List<Integer>> getAllHurufId() {
        return repository.getAllHurufId();
    }
    public LiveData<Huruf> getHurufById(int id) {
        return repository.getHurufById(id);
    }




    public Vehicle getVehicleById(int vid) {
        return repository.getVehicleById(vid);
    }


    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void deleteUser() {
        repository.deleteUser();
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void insertSticker(Sticker[] stickers) {
        repository.insertSticker(stickers);
    }

    public void updateSticker(Sticker sticker) {
        repository.updateSticker(sticker);
    }

    public void deleteSticker(Sticker sticker) {
        repository.deleteSticker(sticker);
    }

    public void deleteAllSticker() {
        repository.deleteAllSticker();
    }


    public void insertCharacter(Character[] c) {
        repository.insertCharacters(c);
    }

    public void updateCharacter(Character c) {
        repository.updateCharacter(c);
    }

    public void deleteAllCharacter() {
        repository.deleteAllCharacter();
    }

    public LiveData<List<SkillJoinCharacter>> getAllSkillCharacters() {
        return repository.getAllSkillByCharacter();
    }


    public List<SkillJoinCharacter> getSkillCharacters(int characterId) {
        return repository.getSkillForCharacter(characterId);
    }

    public List<Skill> getSkillOfCharacters(int characterId) {
        return repository.getAllSkillByCharacter(characterId);
    }

    public void insertVehicle(Vehicle[] c) {
        repository.insertVehicles(c);
    }

    public void updateVehicle(Vehicle c) {
        repository.updateVehicle(c);
    }

    public void deleteAllVehicle() {
        repository.deleteAllVehicle();
    }

    public LiveData<List<Level>> getAllLevel(){
        return repository.getAllLevel();
    }

    public LiveData<List<Badge>> getAllBadge(){
        return repository.getAllBadge();
    }



    public LiveData<Level> getLevelById(int id){
        return repository.getLevelById(id);
    }


    public LiveData<List<Badge>> getBadgeByLevel(int levelId){
        return repository.getBadgesBylevel(levelId);
    }
    public LiveData<List<Badge>> getUnlockedBadgeByLevelId(int levelId){
        return repository.getUnlockedBadgeByLevelId(levelId);
    }

    public LiveData<List<Badge>> getAllAchievedBadge(){
        return repository.getAllAchievedBadge();
    }

    public LiveData<Integer> countGetAllAchievedBadge(){
        return repository.countGetAllAchievedBadge();
    }

    public int resetBadgeStatus(){
        return repository.resetBadgeStatus();
    }

    public void updateBadge(Badge badge){
        repository.updateBadge(badge);
    }

    public LiveData<List<Seasonal>> getAllSeasonal(int code) {
        return repository.getSeasonals(code);
    }

}
