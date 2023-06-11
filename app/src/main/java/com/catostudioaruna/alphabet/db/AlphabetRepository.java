package com.catostudioaruna.alphabet.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AlphabetRepository {
    private UserDao userDao;
    private StickerDao stickerDao;
    private CharacterDao characterDao;
    private SkillDao skillDao;
    private SkillJoinCharacterDao sjkDao;
    private VehicleDao vehicleDao;
    private AnimalDao animalDao;
    private FruitDao fruitDao;
    private HurufDao hurufDao;
    private LevelDao levelDao;
    private BadgeDao badgeDao;
    private SeasonalDao seasonalDao;

    private LiveData<User> user;
    private LiveData<List<Sticker>> stickers;
    private LiveData<List<Character>> characters;
    private LiveData<List<Skill>> skills;
    private LiveData<List<SkillJoinCharacter>> sjks;
    private LiveData<List<Vehicle>> vehicles;
    private LiveData<List<Animal>> animals;
    private LiveData<List<Fruit>> fruits;
    private LiveData<List<Huruf>> hurufs;
    private LiveData<List<Level>> levels;
    private LiveData<List<Badge>> badges;
    private LiveData<List<Seasonal>> seasonals;


    public AlphabetRepository(Application application) {
        AlphabetDatabase db = AlphabetDatabase.getDatabase(application);
        userDao = db.userDao();
        stickerDao = db.stickerDao();
        characterDao = db.characterDao();
        skillDao = db.skillDao();
        user = userDao.getUser();
        stickers = stickerDao.getAllSticker();
        characters = characterDao.getAllCharacter();
        skills = skillDao.getAllSkill();
        sjkDao = db.sjDao();
        vehicleDao = db.vehicleDao();
        animalDao = db.animalDao();
        fruitDao = db.fruitDao();
        hurufDao = db.hurufDao();
        levelDao = db.levelDao();
        badgeDao = db.badgeDao();
        seasonalDao = db.seasonalDao();
        levels = levelDao.getAllLevel();
        badges = badgeDao.getAllBadge();
        seasonals = seasonalDao.getAllSeasonal(1);

    }

    public void insertUser(User user) {
        new insertUserAsync(userDao).execute(user);
    }

    public void deleteUser() {
        new deleteUserAsync(userDao).execute();
    }

    public int updateUser(User user) {
        int result[] = new int[1];

        AlphabetDatabase.databaseWriteExecutor.execute(()->{
            result[0] = userDao.update(user);
        });
        return result[0];

       // new updateUserAsync(userDao).execute(user);
    }


    public void insertSticker(Sticker[] stickers) {
        new insertStickerAsync(stickerDao).execute(stickers);
    }

    public void deleteSticker(Sticker sticker) {
        new deleteStickerAsync(stickerDao).execute(sticker);
    }

    public void deleteAllSticker() {
        new deleteAllSticker(stickerDao).execute();
    }

    public void insertCharacters(Character[] c) {
        new insertCharacterAsync(characterDao).execute(c);
    }

    public void deleteAllCharacter() {
        new deleteAllCharacterAsync(characterDao).execute();
    }

    public void updateCharacter(Character c) {

        new updateCharacterAsync(characterDao).execute(c);
    }

    public void updateSticker(Sticker sticker) {
        new updateStickerAsync(stickerDao).execute(sticker);
    }

    public void insertSkills(Skill[] c) {
        new insertSkillAsync(skillDao).execute(c);
    }

    public void deleteAllSkill() {
        new deleteAllSkillAsync(skillDao).execute();
    }

    public void updateSkill(Skill c) {

        new updateSkillAsync(skillDao).execute(c);
    }

    public void insertVehicles(Vehicle[] v) {
        new insertVehiclesAsync(vehicleDao).execute(v);
    }

    public void deleteAllVehicle() {
        new deleteAllVehicleAsync(vehicleDao).execute();
    }

    public void updateVehicle(Vehicle v) {

        new updateVehicleAsync(vehicleDao).execute(v);
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return vehicleDao.getAllVehicle();
    }

    public Vehicle getVehicleById(int vid) {
        return vehicleDao.getVehicle(vid);
    }

    public LiveData<Sticker> getReward() {
        return stickerDao.getReward();
    }

    public LiveData<List<Badge>> getBadgesBylevel(int levelId){
        return badgeDao.getBadgeByLevelId(levelId);
    }
    public LiveData<List<Badge>> getUnlockedBadgeByLevelId(int levelId){
        return badgeDao.getUnlockedBadgeByLevelId(levelId);
    }



    public LiveData<User> getUser() {
       /* User user = userDao.getUser().getValue();
        if(user != null) {
            int characterId = user.getCharacterId();
            if (characterId > 0) {
                Character c = characterDao.getCharacter(characterId).getValue();
                user.setCharacter(c);
                MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
                userMutableLiveData.setValue(user);

                return userMutableLiveData;
            }
        }*/
        return userDao.getUser();
    }

    public LiveData<List<Sticker>> getStickers() {
        return stickerDao.getAllSticker();
    }

    public LiveData<List<Sticker>> getCollectedSticker() {
        return stickerDao.getCollectedSticker();
    }

    public List<Sticker> getUncollectedSticker() {
        return stickerDao.getUncollectedSticker();
    }

    public LiveData<List<Skill>> getSkills() {
        return skillDao.getAllSkill();
    }

    public LiveData<List<Character>> getCharacters() {
        List<Character> characters = characterDao.getAllCharacter().getValue();

        if (characters != null) {
            List<Character> character = characterDao.getAllCharacter().getValue();
            for (int x = 0; x < character.size(); x++) {
                int id = character.get(x).getId();
                character.get(x).setSkillJoinCharacter(sjkDao.getSkillByCharacter(id));
            }
            MutableLiveData<List<Character>> characterMutableLiveData = new MutableLiveData<>();
            characterMutableLiveData.setValue(character);

            return characterMutableLiveData;
        }
        LiveData<List<Character>> characterLiveData = characterDao.getAllCharacter();
        return characterLiveData;


    }

    public void setSkillCharacter(){

    }

    public LiveData<Character> getCharacterById(int id) {

        return characterDao.getCharacter(id);

    }

    public List<SkillJoinCharacter> getSkillForCharacter(int characterId){
        return sjkDao.getSkillByCharacter(characterId);
    }

    public LiveData<List<SkillJoinCharacter>> getAllSkillByCharacter(){
        return sjkDao.getAllSkillByCharacter();
    }

    public List<Skill> getAllSkillByCharacter(int characterId){
        return sjkDao.getSkillForCharacter(characterId);
    }

    public LiveData<List<Animal>> getAllAnimals() {
        return animalDao.getAllAnimal();
    }
    public LiveData<List<Integer>> getAllAnimalId() {
        return animalDao.allId();
    }

    public LiveData<Animal> getAnimalById(int id) {
        return animalDao.getAnimal(id);
    }

    public LiveData<List<Fruit>> getAllFruits() {
        return fruitDao.getAllFruit();
    }

    public LiveData<List<Integer>> getAllFruitId() {
        return fruitDao.allId();
    }
    public LiveData<Fruit> getFruitById(int id) {
        return fruitDao.getFruit(id);
    }

    public LiveData<List<Huruf>> getAllHurufs() {
        return hurufDao.getAllHuruf();
    }

    public LiveData<List<Integer>> getAllHurufId() {
        return hurufDao.getAllId();
    }

    public LiveData<Huruf> getHurufById(int id) {
        return hurufDao.getHuruf(id);
    }

    public LiveData<List<Level>> getAllLevel(){
        return levelDao.getAllLevel();
    }

    public LiveData<Level> getLevelById(int id){
        return levelDao.getLevel(id);
    }

    public LiveData<List<Badge>> getAllBadge(){
        return badgeDao.getAllBadge();
    }

    public LiveData<Badge> getBadgeById(int id){
        return badgeDao.getBadge(id);
    }

    public LiveData<List<Badge>> getAllAchievedBadge(){
        return badgeDao.getAllAchievedBadge();
    }

    public LiveData<Integer> countGetAllAchievedBadge(){
        return badgeDao.countGetAllAchievedBadge();
    }

    public int resetBadgeStatus(){
        AlphabetDatabase.databaseWriteExecutor.execute(()->{
             badgeDao.resetBadgeStatus();
        });
        return 0;
    }

    public void updateBadge(Badge badge) {
        new updateBadgeAsync(badgeDao).execute(badge);
    }

    public void updateLevelBadge(Level level){
        new updateLevelAsync(levelDao, badgeDao).execute(level);
    }

    public LiveData<List<Seasonal>> getSeasonals(int seasonalCode){
        return seasonalDao.getAllSeasonal(seasonalCode);
    }
    public LiveData<List<Integer>> getAllSeasonalId(int seasonalCode) {
        return seasonalDao.allId(seasonalCode);
    }
    public LiveData<Seasonal> getSeasonalById(int id, int seasonalCode) {
        return seasonalDao.getSeasonal(id, seasonalCode);
    }


    private static class deleteAllSticker extends AsyncTask<Void, Void, Void> {

        private StickerDao stickerDao;

        deleteAllSticker(StickerDao s) {
            this.stickerDao = s;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stickerDao.deleteStickers();
            return null;
        }
    }

    private static class updateStickerAsync extends AsyncTask<Sticker, Void, Void> {

        private StickerDao stickerDao;

        updateStickerAsync(StickerDao s) {
            this.stickerDao = s;
        }

        @Override
        protected Void doInBackground(Sticker... stickers) {
            stickerDao.updateSticker(stickers[0]);
            return null;
        }
    }

    private static class deleteStickerAsync extends AsyncTask<Sticker, Void, Void> {
        private StickerDao stickerDao;

        deleteStickerAsync(StickerDao s) {
            this.stickerDao = s;
        }

        @Override
        protected Void doInBackground(Sticker... stickers) {

            stickerDao.deleteSticker(stickers[0]);

            return null;
        }
    }

    private static class insertStickerAsync extends AsyncTask<Sticker, Void, Void> {
        private StickerDao stickerDao;

        insertStickerAsync(StickerDao s) {
            this.stickerDao = s;
        }

        @Override
        protected Void doInBackground(Sticker... stickers) {
            if (stickers.length > 1) {
                stickerDao.insertStickers(stickers);
            } else {
                stickerDao.insertSticker(stickers[0]);
            }
            return null;
        }
    }

    private static class insertUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        insertUserAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class deleteUserAsync extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        deleteUserAsync(UserDao u) {
            this.userDao = u;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.delete();
            return null;
        }
    }

    private static class updateUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        updateUserAsync(UserDao u) {
            this.userDao = u;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class insertCharacterAsync extends AsyncTask<Character, Void, Void> {

        private CharacterDao characterDao;

        insertCharacterAsync(CharacterDao characterDao) {

            this.characterDao = characterDao;
        }

        @Override
        protected Void doInBackground(Character... characters) {
            characterDao.insertCharacters(characters);
            return null;
        }
    }


    private static class deleteAllCharacterAsync extends AsyncTask<Void, Void, Void> {
        private CharacterDao characterDao;

        deleteAllCharacterAsync(CharacterDao c) {
            this.characterDao = c;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            characterDao.deleteCharacters();
            return null;
        }
    }

    private static class updateCharacterAsync extends AsyncTask<Character, Void, Void> {

        private CharacterDao characterDao;

        updateCharacterAsync(CharacterDao c) {
            this.characterDao = c;
        }

        @Override
        protected Void doInBackground(Character... c) {
            characterDao.updateCharacter(c[0]);
            return null;
        }
    }


    private static class insertSkillAsync extends AsyncTask<Skill, Void, Void> {

        private SkillDao s;

        insertSkillAsync(SkillDao s) {

            this.s = s;
        }

        @Override
        protected Void doInBackground(Skill... ss) {
            s.insertSkills(ss);
            return null;
        }
    }


    private static class deleteAllSkillAsync extends AsyncTask<Void, Void, Void> {
        private SkillDao s;

        deleteAllSkillAsync(SkillDao c) {
            this.s = c;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            s.deleteSkills();
            return null;
        }
    }

    private static class updateSkillAsync extends AsyncTask<Skill, Void, Void> {

        private SkillDao s;

        updateSkillAsync(SkillDao c) {
            this.s = c;
        }

        @Override
        protected Void doInBackground(Skill... c) {
            s.updateSkill(c[0]);
            return null;
        }
    }

    private static class insertVehiclesAsync extends AsyncTask<Vehicle, Void, Void> {

        private VehicleDao v;

        insertVehiclesAsync(VehicleDao v) {

            this.v = v;
        }

        @Override
        protected Void doInBackground(Vehicle... vs) {
            v.insertVehicles(vs);
            return null;
        }
    }

    private static class deleteAllVehicleAsync extends AsyncTask<Void, Void, Void> {
        private VehicleDao v;

        deleteAllVehicleAsync(VehicleDao v) {
            this.v = v;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            v.deleteAllVehicle();
            return null;
        }
    }

    private static class updateVehicleAsync extends AsyncTask<Vehicle, Void, Void> {

        private VehicleDao v;

        updateVehicleAsync(VehicleDao v) {
            this.v = v;
        }

        @Override
        protected Void doInBackground(Vehicle... vs) {
            v.updateVehicle(vs[0]);
            return null;
        }
    }


    private static class updateLevelAsync extends AsyncTask<Level,Void,Void>{

        private LevelDao levelDao;
        private BadgeDao badgeDao;

        updateLevelAsync(LevelDao d, BadgeDao b){
            this.levelDao = d;
            this.badgeDao = b;
        }

        @Override
        protected Void doInBackground(Level... levels) {
            levelDao.updateLevel(levels[0]);
            List<Badge> badges = levels[0].getBadges();
            badgeDao.updateBadges(badges);
            return null;
        }
    }




    private static class updateBadgeAsync extends AsyncTask<Badge,Void,Void>{

        private BadgeDao badgeDao;

        updateBadgeAsync( BadgeDao b){
            this.badgeDao = b;
        }

        @Override
        protected Void doInBackground(Badge... badge) {
            badgeDao.updateBadge(badge[0]);
            return null;
        }
    }




}
