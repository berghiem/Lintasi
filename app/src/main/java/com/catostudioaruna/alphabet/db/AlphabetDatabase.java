package com.catostudioaruna.alphabet.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.catostudioaruna.alphabet.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class, Sticker.class, Character.class, Skill.class,
        SkillJoinCharacter.class, Vehicle.class, Animal.class, Fruit.class,
        Huruf.class, Level.class, Badge.class, Seasonal.class}, version = 44, exportSchema = false)
public abstract class AlphabetDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract StickerDao stickerDao();

    public abstract CharacterDao characterDao();

    public abstract SkillDao skillDao();

    public abstract SkillJoinCharacterDao sjDao();

    public abstract VehicleDao vehicleDao();

    public abstract AnimalDao animalDao();

    public abstract FruitDao fruitDao();

    public abstract HurufDao hurufDao();

    public abstract LevelDao levelDao();

    public abstract BadgeDao badgeDao();

    public abstract SeasonalDao seasonalDao();

    public static volatile AlphabetDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static synchronized AlphabetDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (AlphabetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlphabetDatabase.class, "alphabet_database").
                            fallbackToDestructiveMigration()
                            .build();
                    INSTANCE.populateData(context);

                }
            }
        }
        return INSTANCE;
    }


    public void populateData(Context context) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if (characterDao().count() == 0) {
                    runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            String iconBayu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_c_bayu);
                            String iconBulan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_c_bulan);
                            String bayu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_boyr);
                            String bulan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_girlr2);
                            Character[] characters = new Character[]{
                                   // new Character(10, "Bayu", bayu, iconBayu, R.drawable.boyr),
                                   // new Character(11, "Bulan", bulan, iconBulan, R.drawable.girlr)
                                     new Character(10, "Bayu", bayu, iconBayu, R.drawable.in_boyr),
                                     new Character(11, "Bulan", bulan, iconBulan, R.drawable.in_girlr2)
                            };
                            characterDao().insertCharacters(characters);
                            Skill[] skills = new Skill[]{
                                    new Skill("menjahit"),
                                    new Skill("teliti"),
                                    new Skill("ketangkasan")};
                            skillDao().insertSkills(skills);

                            List<Character> cs = characterDao().getAllCharacter().getValue();
                            if (cs != null) {
                                Log.d("characteraruna", "id : " + cs.get(0).getId());
                            }


                        }
                    });
                    runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            sjDao().insert(new SkillJoinCharacter(1, 10, 100));
                            sjDao().insert(new SkillJoinCharacter(1, 11, 50));
                            sjDao().insert(new SkillJoinCharacter(2, 10, 70));
                            sjDao().insert(new SkillJoinCharacter(2, 11, 100));

                            User user = new User("Bergie", 0, new Timestamp(System.currentTimeMillis()));
                            user.setLastLevel(1);
                            userDao().insert(user);
                            String iconTaxi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_taxi);
                            String iconBus = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_bus);
                            String iconKereta = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_kereta);
                            String iconPerahu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_perahukertas);
                            String iconlayar = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_layar);
                            String iconBalon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_balonudara);
                            String iconPesawat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_pesawat);
                            String iconRoket = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.v_roket);

                            Vehicle[] vehicles = new Vehicle[]{
                                    new Vehicle("Taxi", 1000, iconTaxi, 0, false, false),
                                    new Vehicle("Bus Sekolah", 1000, iconBus, 0, false, false),
                                    new Vehicle("Kereta", 1000, iconKereta, 0, false, false),
                                    new Vehicle("Perahu kertas", 1000, iconPerahu, 1, false, false),
                                    new Vehicle("Layar", 1000, iconlayar, 1, false, false),
                                    new Vehicle("Balon udara", 1000, iconBalon, 2, false, false),
                                    new Vehicle("Pesawat", 1000, iconPesawat, 2, false, false),
                                    new Vehicle("Roket", 1000, iconRoket, 2, false, false),

                            };
                            vehicleDao().insertVehicles(vehicles);
                        }
                    });
                    runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            String anjing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_anjing);
                            String iconanjing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_anjing);
                            String ayam = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_ayam);
                            String iconayam = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_ayam);
                            String badak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_badak);
                            String iconbadak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_badak);
                            String babi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_babi);
                            String iconbabi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_babi);
                            String banteng = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_banteng);//   String bangau = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_bangau);
                            String iconbanteng = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_banteng);//   String bangau = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_bangau);
                            String bebek = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_bebek);
                            String iconbebek = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_bebek);
                            String belalang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_belalang);
                            String iconbelalang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_belalang);
                            String beruang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_beruang);
                            String iconberuang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_beruang);
                            String burung = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_burung);
                            String iconburung = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_burung);
                            String buaya = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_buaya);
                            String iconbuaya = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_buaya);
                            /// String burunghantu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_burunghantu);
                            String burungUnta = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_burungunta);
                            String iconburungUnta = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_burungunta);
                            String dino = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_dino);
                            String icondino = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_dino);
                            String domba = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_domba);
                            String icondomba = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_domba);
                            String flaminggo = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_flaminggo);
                            String iconflaminggo = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_flaminggo);
                            String gajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_gajah);
                            String icongajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_gajah);
                            String harimau = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_harimau);
                            String iconharimau = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_harimau);
                            String iguana = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_iguana);
                            String iconiguana = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_iguana);
                            String ikan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_ikan);
                            String iconikan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_ikan);
                            String itik = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_itik);
                            String iconitik = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_itik);
                            String jerapah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_jerapah);
                            String iconjerapah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_jerapah);
                            String kambing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kambing);
                            String iconkambing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kambing);

                            String klj = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kalajengking);
                            String iconklj = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kalajengking);

                            String kanguru = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kanguru);
                            String iconkanguru = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kanguru);
                            String kepiting = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kepiting);
                            String iconkepiting = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kepiting);


                            String klinci = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kelinci2);
                            String iconklinci = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kelinci);
                            String kijang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kijang);
                            String iconkijang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kijang);
                            String koala = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_koala);
                            String iconkoala = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_koala);
                            String kucing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kucing);
                            String iconkucing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kucing);
                            String kuda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kuda);
                            String iconkuda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kuda);
                            String kudanil = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kudanil);
                            String iconkudanil = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kudanil);
                            String kodok = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kodok);
                            String iconkodok = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kodok);

                            //   String kumbang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kumbang);
                            String kupu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kupu2);
                            String iconkupu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kupu2);
                            String kura = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_kura2);
                            String iconkura = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_kura2);
                            String landak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_landak);
                            String iconlandak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_landak);
                            String lebah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_lebah);
                            String iconlebah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_lebah);
                            String macan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_macan);
                            String iconmacan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_macan);
                            String monyet = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_monyet);
                            String iconmonyet = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_monyet);
                            String onta = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_onta);
                            String icononta = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_onta);
                            String panda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_panda);
                            String iconpanda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_panda);
                            String pinguin = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_pinguin);
                            String iconpinguin = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_pinguin);
                            //   String rakun = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_rakun);
                            String rubah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_rubah);
                            String iconrubah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_rubah);
                            String rusa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_rusa);
                            String iconrusa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_rusa);
                            String sapi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_sapi);
                            String iconsapi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_sapi);

                            String semut = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_semut);
                            String iconsemut = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_semut);
                            String serigala = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_serigala);
                            String iconserigala = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_serigala);
                            String singa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_singa);
                            String iconsinga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_singa);
                            String tupai = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_tupai);
                            String icontupai = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_tupai);
                            String ular = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_ular);
                            String iconular = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_ular);
                            String ulat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_ulat);
                            String iconulat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_ulat);
                            String zebra = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.z_zebra);
                            String iconzebra = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.zi_zebra);


                            String audioAyam = context.getApplicationContext().getResources().getResourceEntryName(R.raw.ayamhp);
                            String audioAnjing = context.getApplicationContext().getResources().getResourceEntryName(R.raw.anjiunghp);

                            Animal[] animals = new Animal[]{

                                    new Animal("ayam", 3, ayam, 0, iconayam, R.drawable.z_ayam),
                                    new Animal("anjing", 2, anjing, 0, iconanjing, R.drawable.z_anjing),
                                    new Animal("babi", 3, babi, 0, iconbabi, R.drawable.z_babi),
                                    new Animal("badak", 3, badak, 0, iconbadak, R.drawable.z_badak),

                                    new Animal("banteng", 3, banteng, 0, iconbanteng, R.drawable.z_banteng),
                                    new Animal("bebek", 2, bebek, 0, iconbebek, R.drawable.z_bebek),
                                    new Animal("belalang", 1, belalang, 0, iconbelalang, R.drawable.z_belalang),

                                    new Animal("beruang", 2, beruang, 0, iconberuang, R.drawable.z_beruang),
                                    new Animal("burung", 1, burung, 0, iconburung, R.drawable.z_burung),
                                    new Animal("buaya", 2, buaya, 0, iconbuaya, R.drawable.z_buaya),
                                    new Animal("burung unta", 2, burungUnta, 0, iconburungUnta, R.drawable.z_burungunta),
                                    // new Animal("cumi-cumi",2,cumi,0),
                                    new Animal("dinosaurus", 4, dino, 0, icondino , R.drawable.z_dino),
                                    new Animal("domba", 3, domba, 0, icondomba, R.drawable.z_domba),
                                    new Animal("flaminggo", 3, flaminggo, 0, iconflaminggo, R.drawable.z_flaminggo),
                                    new Animal("gajah", 4, gajah, 0, icongajah , R.drawable.z_gajah),
                                    // new Animal("gurita",3,gurita,0),
                                    new Animal("harimau", 3, harimau, 0, iconharimau, R.drawable.z_harimau),
                                    new Animal("iguana", 2, iguana, 0, iconiguana, R.drawable.z_iguana),
                                    new Animal("itik", 1, itik, 0, iconitik, R.drawable.z_itik),

                                    new Animal("ikan", 2, ikan, 0, iconikan, R.drawable.z_ikan),
                                    // new Animal("jangkrik",1,jangkrik,0),
                                    new Animal("jerapah", 4, jerapah, 0, iconjerapah , R.drawable.z_jerapah),
                                    //   new Animal("kadal",1,kdal,0),
                                    new Animal("kalajengking", 2, klj, 0, iconklj, R.drawable.z_kalajengking),
                                    new Animal("kambing", 3, kambing, 0, iconkambing, R.drawable.z_kambing),
                                    new Animal("kanguru", 3, kanguru, 0, iconkanguru, R.drawable.z_kanguru),
                                    new Animal("kelinci", 2, klinci, 0, iconklinci, R.drawable.z_kelinci),
                                    //    new Animal("kepik",1,kepik,0),
                                    new Animal("kepiting", 2, kepiting, 0, iconkepiting, R.drawable.z_kepiting),
                                    new Animal("kijang", 3, kijang, 0, iconkijang,R.drawable.z_kijang),
                                    new Animal("koala", 2, koala, 0, iconkoala, R.drawable.z_koala),
                                    new Animal("kucing", 2, kucing, 0, iconkucing, R.drawable.z_kucing),
                                    new Animal("kuda", 3, kuda, 0, iconkuda, R.drawable.z_kuda),
                                    new Animal("kudanil", 3, kudanil, 0, iconkudanil, R.drawable.z_kudanil),
                                    new Animal("kodok", 2, kodok, 0, iconkodok, R.drawable.z_kodok),

                                    new Animal("kupu-kupu", 1, kupu, 0, iconkupu , R.drawable.z_kupu2),
                                    new Animal("kura-kura", 2, kura, 0, iconkura , R.drawable.z_kura2),
                                    new Animal("landak", 2, landak, 0, iconlandak , R.drawable.z_landak),
                                    new Animal("lebah", 1, lebah, 0, iconlebah, R.drawable.z_lebah),

                                    //  new Animal("lipan",1,lipan,0),
                                    new Animal("macan", 3, macan, 0, iconmacan , R.drawable.z_macan),
                                    new Animal("monyet", 3, monyet, 0, iconmonyet, R.drawable.z_monyet),
                                    //   new Animal("merak",2,merak,0),
                                    new Animal("onta", 4, onta, 0, icononta, R.drawable.z_onta),
                                    new Animal("panda", 3, panda, 0, iconpanda, R.drawable.z_panda),
                                    new Animal("pinguin", 2, pinguin, 0, iconpinguin, R.drawable.z_pinguin),
                                    // new Animal("rakun",2,rakun,0),
                                    new Animal("rubah", 2, rubah, 0, iconrubah, R.drawable.z_rubah),
                                    new Animal("rusa", 3, rusa, 0, iconrusa, R.drawable.z_rusa),
                                    new Animal("sapi", 1, sapi, 0, iconsapi, R.drawable.z_sapi),

                                    new Animal("semut", 1, semut, 0, iconsemut, R.drawable.z_semut),
                                    new Animal("serigala", 3, serigala, 0, iconserigala, R.drawable.z_serigala),
                                    new Animal("singa", 3, singa, 0, iconsinga, R.drawable.z_singa),
                                    new Animal("tupai", 1, tupai, 0, icontupai, R.drawable.z_tupai),
                                    new Animal("ular", 3, ular, 0, iconular, R.drawable.z_ular),
                                    new Animal("ulat", 1, ulat, 0, iconulat, R.drawable.z_ulat),
                                    new Animal("zebra", 4, zebra, 0, iconzebra, R.drawable.z_zebra)
                            };
                            animalDao().insertAnimals(animals);

                        }
                    });
                    runInTransaction(new Runnable() {
                        @Override
                        public void run() {

                            String alpukat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_alpukat);
                            String iconalpukat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_alpukat);
                            String anggur = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_anggurr);
                            String iconanggur = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_anggur);
                            String apel = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_apel);
                            String iconapel = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_apel);
                            String bbombay = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_bawangbombay);
                            String iconbombay = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_bawangbombay);
                            String bputih = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_bawangputih);
                            String iconbputih = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_bawangputih);
                            String bmerah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_bawangmerah);
                            String iconbmerah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_bawangmerah);
                            String belimbing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_belimbing);
                            String iconbelimbing = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_belimbing);
                            String bit = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_bit);
                            String iconbit = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_bit);
                            String brokoli = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_brokoli);
                            String iconbrokoli = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_brokoli);
                            String cabai = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_cabai);
                            String iconcabai = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_cabai);
                            String ceri = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_cheri);
                            String iconceri = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_ceri);
                            String delima = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_delima);
                            String icondelima = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_delima);
                            String durian = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_durian);
                            String icondurian = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_durian);
                            String jagung = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_jagung);
                            String iconjagung = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_jagung);
                            String jamur = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_jamur);
                            String iconjamur = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_jamur);
                            String jeruk = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_jeruk);
                            String iconjeruk = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_jeruk);
                            String kelapa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_kelapa);
                            String iconkelapa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_kelapa);
                            String kiwi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_kiwi);
                            String iconkiwi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_kiwi);
                            String labu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_labu);
                            String iconlabu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_labu);

                            String leci = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_leci);
                            String iconleci = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_leci);


                            String lemon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_lemonn);
                            String iconlemon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_lemon);
                            String lobak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_lobak);
                            String iconlobak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_lobak);
                            String mangga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_manggaa);
                            String iconmangga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_mangga);

                            String manggis = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_manggis);
                            String iconmanggis = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_manggis);
                            String markisa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_markisa);
                            String iconmarkisa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_markisa);

                            String melon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_melon);
                            String iconmelon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_melon);


                            String nanas = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_nanas);
                            String iconnanas = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_nanas);
                            String naga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_naga);
                            String iconnaga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_naga);
                            String pepaya = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_pepayaa);
                            String iconpepaya = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_pepaya);
                            String pir = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_pir);
                            String iconpir = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_pir);
                            String pisang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_pisang);
                            String iconpisang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_pisang);

                            String rambutan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_rambutann);
                            String iconrambutan= context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_rambutan);
                            String semangka = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_semangka);
                            String iconsemangka = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_semangka);
                            String strawberry = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_strawberryy);
                            String iconStrawberry = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_strawberry);

                            String terong = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_terong);
                            String iconterong= context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_terong);

                            String timun = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_timun);
                            String icontimun= context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_timun);



                            String tomat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_tomat);
                            String icontomat = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_tomat);
                            String wortel = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_wortel);
                            String iconwortel = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.fi_wortel);



                            Fruit[] fruits = new Fruit[]{

                                    new Fruit("alpukat", alpukat, iconalpukat, 0),
                                    new Fruit("anggur", anggur, iconanggur, 0),
                                    new Fruit("apel", apel, iconapel, 0),
                                    new Fruit("bawang bombay", bbombay, iconbombay, 0),
                                    new Fruit("bawang putih", bputih, iconbputih, 0),
                                    new Fruit("bawang merah", bmerah, iconbmerah, 0),
                                    new Fruit("belimbing", belimbing, iconbelimbing, 0),
                                    new Fruit("bit", bit, iconbit, 0),
                                    new Fruit("brokoli", brokoli, iconbrokoli, 0),
                                    new Fruit("cabai", cabai, iconcabai, 0),
                                    new Fruit("ceri", ceri, iconceri, 0),
                                    new Fruit("delima", delima, icondelima, 0),
                                    new Fruit("durian", durian, icondurian, 0),
                                    new Fruit("jagung", jagung, iconjagung, 0),
                                    new Fruit("jamur", jamur, iconjamur, 0),
                                    new Fruit("jeruk", jeruk, iconjeruk, 0),
                                    new Fruit("kelapa", kelapa, iconkelapa, 0),
                                    new Fruit("kiwi", kiwi, iconkiwi, 0),
                                    new Fruit("labu", labu, iconlabu, 0),

                                    new Fruit("leci", leci, iconleci, 0),
                                    new Fruit("lemon", lemon, iconlemon, 0),
                                    new Fruit("lobak", lobak, iconlobak, 0),

                                    new Fruit("mangga", mangga, iconmangga, 0),
                                    new Fruit("manggis", manggis, iconmanggis, 0),
                                    new Fruit("markisa", markisa, iconmarkisa, 0),
                                    new Fruit("melon", melon, iconmelon, 0),
                                    new Fruit("naga", naga, iconnaga, 0),
                                    new Fruit("pepaya", pepaya, iconpepaya, 0),
                                    new Fruit("pir", pir, iconpir, 0),
                                    new Fruit("pisang", pisang, iconpisang, 0),
                                    new Fruit("rambutan", rambutan, iconrambutan, 0),
                                    new Fruit("semangka", semangka, iconsemangka, 0),
                                    new Fruit("strawberi", strawberry, iconStrawberry, 0),
                                    new Fruit("terong", terong, iconterong, 0),
                                    new Fruit("timun", timun, icontimun, 0),

                                    new Fruit("tomat", tomat, icontomat, 0),
                                    new Fruit("wortel", wortel, iconwortel, 0),


                            };
                            fruitDao().insertFruits(fruits);

                        }
                    });

                    runInTransaction(new Runnable() {
                        @Override
                        public void run() {

                            String a = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.f_apel);
                            String b = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_bunga);
                            String c = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_c);
                            String d = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_d);
                            String e = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_e);
                            String f = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_fdua);
                            String g = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_g);
                            String h = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_hdua);
                            String i = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_i);
                            String j = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_jaket);
                            String k = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_kauskaki);
                            String l = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_l);
                            String m = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_m);
                            String n = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_n);
                            String o = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_o);
                            String p = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.n_nine);
                            String q = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_q);
                            String r = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_rotie);
                            String s = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_s);
                            String t = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.teman);
                            String u = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_u);
                            String v = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_v);
                            String w = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_w);
                            String x = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_x);
                            String y = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_y);
                            String z = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.l_z);


                            String qa = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_a);
                            String qb = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_b);
                            String qc = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_c);
                            String qd = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_d);
                            String qe = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_e);
                            String qf = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_f);
                            String qg = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_g);
                            String qh = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_h);
                            String qi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_i);
                            String qj = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_j);
                            String qk = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_k);
                            String ql = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_l);
                            String qm = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_m);
                            String qn = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_n);
                            String qo = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_o);
                            String qp = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_p);
                            String qq = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_q);
                            String qr = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_r);
                            String qs = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_s);
                            String qt = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_t);
                            String qu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_u);
                            String qv = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_v);
                            String qw = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_w);
                            String qx = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_x);
                            String qy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_y);
                            String qz = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.a_z);


                            Huruf[] hurufs = new Huruf[]{

                                    new Huruf("A a", a, "", "a", 0, qa),
                                    new Huruf("B b", b, "", "b", 0, qb),
                                    new Huruf("C c", c, "", "c", 0, qc),
                                    new Huruf("D d", d, "", "d", 0, qd),
                                    new Huruf("E e", e, "", "e", 0, qe),
                                    new Huruf("F f", f, "", "f", 0, qf),
                                    new Huruf("G g", g, "", "g", 0, qg),


                                    new Huruf("H h", h, "", "h", 0, qh),
                                    new Huruf("I i", i, "", "i", 0, qi),
                                    new Huruf("J j", j, "", "j", 0, qj),
                                    new Huruf("K k", k, "", "k", 0, qk),
                                    new Huruf("L l", l, "", "l", 0, ql),
                                    new Huruf("M m", m, "", "m", 0, qm),
                                    new Huruf("N n", n, "", "n", 0, qn),

                                    new Huruf("O o", o, "", "o", 0, qo),
                                    new Huruf("P p", p, "", "p", 0, qp),
                                    new Huruf("Q q", q, "", "q", 0, qq),
                                    new Huruf("R r ", r, "", "r", 0, qr),
                                    new Huruf("S s", s, "", "s", 0, qs),
                                    new Huruf("T t", t, "", "t", 0, qt),
                                    new Huruf("U u", u, "", "u", 0, qu),

                                    new Huruf("V v", v, "", "v", 0, qv),
                                    new Huruf("W w", w, "", "w", 0, qw),
                                    new Huruf("X x", x, "", "x", 0, qx),
                                    new Huruf("Y y", y, "", "y", 0, qy),
                                    new Huruf("Z z", z, "", "z", 0, qz)
                            };
                            hurufDao().insertHurufs(hurufs);

                        }
                    });
                    runInTransaction(new Runnable() {
                        @Override
                        public void run() {

                            String healthyBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_healthy_boy);
                            String healthyGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_health_girl);
                            String smartBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_smartboy);
                            String smartGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_smartgirl);
                            String bayu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.boyr);
                            String bulan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.girlr);
                            String cheerBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_healthy_boy);
                            String cheerGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_cheergirl);
                            String cBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_cheerboy);
                            String winnerGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_girl_winner);
                            String winnerBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_l_winner_boy);

                            Level[] levels = new Level[]{
                                    new Level("Anak Sehat", healthyGirl, healthyBoy, 0 ,
                                            R.drawable.g_l_health_girl, R.drawable.g_l_healthy_boy),

                                    new Level("Anak Pintar", smartGirl, smartBoy, 0,
                                            R.drawable.g_l_smartgirl, R.drawable.g_l_smartboy),
                                    new Level("Anak Ceria", cheerGirl, cBoy, 0,
                                            R.drawable.g_l_cheergirl, R.drawable.g_l_cheerboy),
                                    new Level("Anak Sholeh", bulan, bayu, 0,
                                            R.drawable.girlr, R.drawable.boyr),
                                    new Level("Sang Juara", winnerGirl, winnerBoy, 0,
                                            R.drawable.g_girl_winner, R.drawable.g_l_winner_boy),
                            };
                            levelDao().insertLevels(levels);

                            String salad = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_salad);
                            String getsaladBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_getfruitboy);
                            String getsaladGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_getfruitgirl);

                            String susu = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_susu);
                            String getMilkBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_drink_boy_1);
                            String getMilkGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_drinkgirl);

                            Badge bsalad = new Badge(1, salad, getsaladGirl, getsaladBoy, "mendapat buah-buahan\n dari ibu", false, 1,R.drawable.g_getfruitboy, R.drawable.g_getfruitgirl);
                            Badge bsusu = new Badge(2, susu, getMilkGirl, getMilkBoy, "mendapat segelas susu\n dari ibu", false, 1 , R.drawable.g_drink_boy_1, R.drawable.g_drinkgirl);
                            List<Badge> anakSehat = new ArrayList<>();
                            anakSehat.add(bsalad);
                            anakSehat.add(bsusu);


                            String sepeda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_sepeda);
                            String playBikeBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_bike_boy);
                            String playBikeGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_bike_girl);

                            String buku = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_buku);
                            String bedBookBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_boybook);
                            String bedBookGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_girlbook_1);

                            Badge bsepeda = new Badge(3, sepeda, playBikeGirl, playBikeBoy, "ayah akan bermain sepeda bersamamu!",
                                    false, 2, R.drawable.g_bike_boy, R.drawable.g_bike_girl);
                            Badge bbuku = new Badge(4, buku, bedBookGirl, bedBookBoy, "ibu akan membacakan dongeng untukmu sebelum tidur",
                                    false, 2, R.drawable.g_boybook, R.drawable.g_girlbook_1);
                            List<Badge> anakPintar = new ArrayList<>();
                            anakPintar.add(bsepeda);
                            anakPintar.add(bbuku);


                            String pelukan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_hug);
                            String hugGirl = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_girl_hug);
                            String hugBoy = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_boy_hug);

                            String grand = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_foto);
                            String girlgrand = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_girl_grand);
                            String boygrand = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_boy_grand);

                            Badge bpelukan = new Badge(5, pelukan, hugGirl, hugBoy, "mendapat hadiah pelukan sayang dari ibu",
                                    false, 3, R.drawable.g_boy_hug, R.drawable.g_girl_hug);
                            Badge bfoto = new Badge(6, grand, girlgrand, boygrand, "menghabiskan waktu bersama kakek dan nenek tersayang",
                                    false, 3, R.drawable.g_boy_grand, R.drawable.g_girl_grand);
                            List<Badge> anakCeria = new ArrayList<>();
                            anakCeria.add(bpelukan);
                            anakCeria.add(bfoto);

                            String kue = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_kue);
                            String girlKue = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_girl_cake);
                            String boyKue = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_boy_cake);

                            String balon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_balon);
                            String girlbalon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_girl_balon);
                            String boybalon = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.g_boy_baloon);

                            Badge bkue = new Badge(7, kue, girlKue, boyKue, "mendapat kue lezat buatan ibu",
                                    false, 4, R.drawable.g_boy_cake, R.drawable.g_girl_cake);
                            Badge bbalon = new Badge(8, balon, girlbalon, boybalon, "mendapat sebuah balon dari ayah",
                                    false, 4, R.drawable.g_boy_baloon, R.drawable.g_girl_balon);
                            List<Badge> anakSholeh = new ArrayList<>();
                            anakSholeh.add(bkue);
                            anakSholeh.add(bbalon);

                            badgeDao().insertBadges(anakSehat);
                            badgeDao().insertBadges(anakPintar);
                            badgeDao().insertBadges(anakCeria);
                            badgeDao().insertBadges(anakSholeh);


                        }
                    });




                 runInTransaction(new Runnable() {
                     @Override
                     public void run() {
                         String iconbendera = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_bendera);
                         String bendera = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_bendera);
                         String icongaruda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_garuda);
                         String garuda = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_garuda);
                         String iconpaskibra = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_paskibra);
                         String paskibra = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_paskibra);

                         String iconbunga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_bbangkai);
                         String bunga = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_raflesia);

                         String icontaribali = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_taribali);
                         String taribali = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_taribali);
                         String icontarigong = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_tarigong);
                         String tarigong = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_tarigong);
                         String iconkarapansapi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_karapansapi);
                         String karapansapi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_karapansapi);

                         String iconbarong = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_barong);
                         String barong = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_barong);

                         String wayang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_wayang);
                         String iconwayang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_wayang);

                         String iconondel = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_ondel);
                         String ondel = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_ondel);

                         String iconhanoman = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_hanoman);
                         String hanoman = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_hanoman);

                         String iconbajaj = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_bajaj);
                         String bajaj = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_bajaj);
                         String iconjanur = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_janur);
                         String janur = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_janurkuning);
                         String iconbecak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_becak);
                         String becak = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_becak);

                         String icongadang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_gadang);
                         String gadang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_gadang);
                         String iconmonas = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_monas);
                         String monas = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_monas);
                         String iconsurabaya = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_l_surabaya);
                         String surabaya = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_l_surabaya);
                         String candi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_borobudur);
                         String iconCandi = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_borobudur);
                         String tongkongan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_tongkongan);
                         String icontongkongan = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_tongkongan);

                         String iconmudik = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_mudik);
                         String mudik = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_mudik);
                         String iconbalapkarung = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_balapkarung);
                         String balapkarung = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_balapkarung);
                         String iconlombakerupuk = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_makankerupuk);
                         String lombamakankerupuk = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_makankerupuk);
                         String icontariktambang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_tariktambang);
                         String tariktambang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_tariktambang);
                         String iconpanjatpinang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_panjatpinang);
                         String panjatpinang = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.in_panjatpinang);

                         String seasonalName = "17 Agustus";
                         int seasonalCode = 1;

                         Seasonal[] seasonals =  new Seasonal[]{
                                 new Seasonal("bendera merah putih", bendera, iconbendera,0,seasonalCode,seasonalName),
                                 new Seasonal("garuda pancasila", garuda, icongaruda,0,seasonalCode,seasonalName),
                                 new Seasonal("paskibra", paskibra, iconpaskibra,0,seasonalCode,seasonalName),

                                 new Seasonal("bunga Rafflesia", bunga, iconbunga,0,seasonalCode,seasonalName),
                                 new Seasonal("tari pendet", taribali, icontaribali,0,seasonalCode,seasonalName),
                                 new Seasonal("tari gong", tarigong, icontarigong,0,seasonalCode,seasonalName),

                                 new Seasonal("barong", barong, iconbarong,0,seasonalCode,seasonalName),
                                 new Seasonal("janur kuning", janur,iconjanur,0,seasonalCode,seasonalName),
                                 new Seasonal("karapan sapi", karapansapi, iconkarapansapi,0,seasonalCode,seasonalName),
                                 new Seasonal("hanoman", hanoman, iconhanoman,0,seasonalCode,seasonalName),
                                 new Seasonal("wayang ", wayang, iconwayang,0,seasonalCode,seasonalName),
                                 new Seasonal("ondel-ondel", ondel, iconondel,0,seasonalCode,seasonalName),

                                 new Seasonal("becak", becak,iconbecak,0,seasonalCode,seasonalName),
                                // new Seasonal("bemo", bemo,iconbemo,0,seasonalCode,seasonalName),
                                 new Seasonal("bajaj", bajaj,iconbajaj,0,seasonalCode,seasonalName),

                                 new Seasonal("patung\nsuro dan boyo", surabaya,iconsurabaya,0,seasonalCode,seasonalName),
                                 new Seasonal("rumah gadang", gadang,icongadang,0,seasonalCode,seasonalName),
                                 new Seasonal("monas", monas,iconmonas,0,seasonalCode,seasonalName),
                                 new Seasonal("candi borobudur", candi,iconCandi,0,seasonalCode,seasonalName),
                                 new Seasonal("rumah tongkongan", tongkongan,icontongkongan,0,seasonalCode,seasonalName),


                                 new Seasonal("mudik", mudik,iconmudik,0,seasonalCode,seasonalName),
                                 new Seasonal("balap karung", balapkarung,iconbalapkarung,0,seasonalCode,seasonalName),
                                 new Seasonal("lomba makan kerupuk", lombamakankerupuk,iconlombakerupuk,0,seasonalCode,seasonalName),
                                 new Seasonal("panjat pinang", panjatpinang,iconpanjatpinang,0,seasonalCode,seasonalName),
                                 new Seasonal("lomba tarik tambang", tariktambang,icontariktambang,0,seasonalCode,seasonalName),


                         };
                         seasonalDao().insertSeasonals(seasonals);

                     }
                 });

                 runInTransaction(new Runnable() {
                     @Override
                     public void run() {

                         String mataGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bp_m_gajah);
                         String telingaGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bp_t_gajah);
                         String belalaiGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bp_b_gajah);
                         String gadingGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bp_g_gajah);
                         String kakiGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.inf_gadang);
                         String ekorGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bp_e_gajah);
                         String badanGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bp_badan_gajah);

                         String iconmataGajah = context.getApplicationContext().getResources().getResourceEntryName(R.drawable.bpi_m_gajah);


                         Bodypart[] bodyparts =  new Bodypart[]{
                                 new Bodypart("mata","",mataGajah,15),
                                 new Bodypart("telinga","",telingaGajah,15),
                                 new Bodypart("belalai","",belalaiGajah,15),
                                 new Bodypart("gading","",gadingGajah,15),
                                 new Bodypart("kaki","",kakiGajah,15),
                                 new Bodypart("ekor","",ekorGajah,15),
                                 new Bodypart("badan","",badanGajah,15),


                         };

                     }
                 });

                }
                return null;
            }
        }.execute();

    }


}
