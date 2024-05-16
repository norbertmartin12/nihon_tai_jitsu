package org.ntj_workout.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Database {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    public static final String LOCAL_CACHE_JSON = "localCache.json";
    private List<Question> questionList;
    private String initDescription;

    public Database init(Initiator initiator, Context context) {
        loadQuestionList(initiator, context);
        return this;
    }

    public Revision start(Level level, Type type) {
        if (this.questionList == null) {
            throw new IllegalStateException("call init first");
        }

        List<Question> newList = new LinkedList<>(this.questionList);
        if (level != Level.BLACK) {
            newList = newList.stream().filter(question -> question.getLevel().ordinal() <= level.ordinal()).collect(Collectors.toList());
        }
        if (type != Type.BOTH) {
            newList = newList.stream().filter(question -> question.getType() == type).collect(Collectors.toList());
        }

        if (newList.isEmpty()) {
            return null;
        }
        Collections.shuffle(newList);
        return new Revision(newList);
    }

    private void loadQuestionList(final Initiator initiator, Context context) {
        if (this.questionList != null && !this.questionList.isEmpty()) {
            initiator.loaded(this);
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final boolean isActiveNetworkNonMetered = !connectivityManager.isActiveNetworkMetered();
        EXECUTOR_SERVICE.submit(() -> {
            JSONObject jsonObject = null;
            String loadingMode = null;
            if (isActiveNetworkNonMetered) {
                jsonObject = getOnlineQuestionJson();
                loadingMode = "online";
                try {
                    saveAsLocalCache(context, jsonObject);
                } catch (IOException e) {
                    Log.e("data_pull", "saveAsLocalCache fails: " + e.getLocalizedMessage());
                }
            }
            if (jsonObject == null) {
                try {
                    jsonObject = readLocalCache(context);
                    loadingMode = "online-cache [" + getLocalCacheLastModified(context) + "]";
                } catch (FileNotFoundException | JSONException e) {
                    Log.e("data_pull", "readLocalCache fails: " + e.getLocalizedMessage());
                }
            }

            List<Question> list = parseJsonQuestionList(jsonObject);
            if (list.isEmpty()) {
                list = getOfflineQuestionList();
                loadingMode = "embedded";
            }
            initDescription = loadingMode + " - " +  list.size() + " questions";
            this.questionList = Collections.unmodifiableList(list);
            initiator.loaded(this);
        });
    }

    private void saveAsLocalCache(Context context, JSONObject jsonObject) throws IOException {
        if (jsonObject == null) {
            return;
        }
        File localCache = new File(context.getNoBackupFilesDir(), LOCAL_CACHE_JSON);
        FileOutputStream fileOutputStream = new FileOutputStream(localCache);
        fileOutputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
    }

    private JSONObject readLocalCache(Context context) throws FileNotFoundException, JSONException {
        File localCache = new File(context.getNoBackupFilesDir(), LOCAL_CACHE_JSON);
        return new JSONObject(new BufferedReader(new FileReader(localCache)).lines().collect(Collectors.joining()));
    }
    private Date getLocalCacheLastModified(Context context) {
        File localCache = new File(context.getNoBackupFilesDir(), LOCAL_CACHE_JSON);
        return new Date (localCache.lastModified());
    }

    private static JSONObject getOnlineQuestionJson() {
        String sheetsUrl = "https://sheets.googleapis.com/v4/spreadsheets/1ymOsztjiYsp3w9OS4XdOGtqcJQyHXiRPpR8jmrWTFOg/values/human?alt=json&key=AIzaSyBO4GJYpO_kRby3sle3HE10u3967eJN0KA";
        HttpsURLConnection urlConnection = null;
        JSONObject jsonObject = null;
        try {
            urlConnection = (HttpsURLConnection) new URL(sheetsUrl).openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.connect();
            jsonObject = new JSONObject(new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).lines().collect(Collectors.joining()));
        } catch (IOException | JSONException e) {
            Log.e("getOnlineQuestionJson", Objects.requireNonNull(e.getLocalizedMessage()));
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonObject;
    }

    private static List<Question> parseJsonQuestionList(JSONObject jsonObject) {
        if (jsonObject == null) {
            return Collections.emptyList();
        }
        List<Question> loadedQuestionList = new LinkedList<>();
        try {
            JSONArray valuesArray = jsonObject.getJSONArray("values");
            for (int i = 1; i < valuesArray.length(); i++) {
                JSONArray line = valuesArray.getJSONArray(i);
                int id = line.getInt(0);
                Type type;
                {
                    String stringType = line.getString(1);
                    switch (stringType.toLowerCase()) {
                        case "pratique":
                            type = Type.PRACTICE;
                            break;
                        case "budo":
                            type = Type.THEORY;
                            break;
                        default:
                            Log.e("parse type", "ignored line, id: " + id + " - invalid value: [" + stringType + "]");
                            continue;
                    }
                }
                Level level;
                {
                    String stringLevel = line.getString(2);
                    switch (stringLevel.toLowerCase()) {
                        case "jaune":
                            level = Level.YELLOW;
                            break;
                        case "orange":
                            level = Level.ORANGE;
                            break;
                        case "verte":
                            level = Level.GREEN;
                            break;
                        case "marron":
                            level = Level.BROWN;
                            break;
                        case "bleue":
                            level = Level.BLUE;
                            break;
                        case "noire":
                            level = Level.BLACK;
                            break;
                        default:
                            Log.e("parse level", "ignored line, id: " + id + " - invalid value: [" + stringLevel + "]");
                            continue;
                    }
                }
                String label = line.getString(3);

                String textAnswer = "";
                if (line.length() > 4) {
                    textAnswer = line.getString(4);
                }
                String imageAnswerUrl = "";
                if (line.length() > 5) {
                    imageAnswerUrl = line.getString(5);
                }
                String videoAnswerUrl = "";
                if (line.length() > 6) {
                    videoAnswerUrl = line.getString(6);
                }
                loadedQuestionList.add(new Question(id, level, type, label, textAnswer, imageAnswerUrl, videoAnswerUrl));
            }
        } catch (JSONException e) {
            Log.e("JSONException", Objects.requireNonNull(e.getLocalizedMessage()));
        }
        Log.i("sheets_pulling", "contains " + loadedQuestionList.size() + " questions");
        return loadedQuestionList;
    }

    private static List<Question> getOfflineQuestionList() {
        //"https://drive.google.com/uc?export=view&id="
        List<Question> localQuestionList = new LinkedList<>();

        localQuestionList.add(new Question(1,Level.YELLOW,Type.PRACTICE,"Base 1 par atemi","","","https://www.nihon-tai-jitsu.fr/video-app/001.mp4"));
        localQuestionList.add(new Question(2,Level.YELLOW,Type.PRACTICE,"Base 2 par atemi","","","https://www.nihon-tai-jitsu.fr/video-app/002.mp4"));
        localQuestionList.add(new Question(3,Level.YELLOW,Type.PRACTICE,"Base 3 par atemi (deux formes)","","","https://www.nihon-tai-jitsu.fr/video-app/003.mp4"));
        localQuestionList.add(new Question(4,Level.YELLOW,Type.PRACTICE,"Base 4 par atemi (deux formes)","","","https://www.nihon-tai-jitsu.fr/video-app/004.mp4"));
        localQuestionList.add(new Question(5,Level.YELLOW,Type.PRACTICE,"Base 5 par atemi","","","https://www.nihon-tai-jitsu.fr/video-app/005.mp4"));
        localQuestionList.add(new Question(6,Level.YELLOW,Type.PRACTICE,"Base 6 par atemi","","","https://www.nihon-tai-jitsu.fr/video-app/006.mp4"));
        localQuestionList.add(new Question(7,Level.YELLOW,Type.PRACTICE,"Base 7 par atemi","","","https://www.nihon-tai-jitsu.fr/video-app/007.mp4"));
        localQuestionList.add(new Question(8,Level.YELLOW,Type.PRACTICE,"Base 8 par atemi ( (deux formes)","","","https://www.nihon-tai-jitsu.fr/video-app/008.mp4"));
        localQuestionList.add(new Question(9,Level.YELLOW,Type.PRACTICE,"Faire son noeud de ceinture","","","https://www.nihon-tai-jitsu.fr/video-app/009.mp4"));
        localQuestionList.add(new Question(10,Level.YELLOW,Type.PRACTICE,"Salut debout","","","https://www.nihon-tai-jitsu.fr/video-app/010.mp4"));
        localQuestionList.add(new Question(11,Level.YELLOW,Type.PRACTICE,"Position ZENKUTSU DACHI","","","https://www.nihon-tai-jitsu.fr/video-app/011.mp4"));
        localQuestionList.add(new Question(12,Level.YELLOW,Type.PRACTICE,"Position KOKUTSU DACHI","","","https://www.nihon-tai-jitsu.fr/video-app/012.mp4"));
        localQuestionList.add(new Question(13,Level.YELLOW,Type.PRACTICE,"Position SHIKO DACHI","","","https://www.nihon-tai-jitsu.fr/video-app/013.mp4"));
        localQuestionList.add(new Question(14,Level.YELLOW,Type.PRACTICE,"Salut à genou","","","https://www.nihon-tai-jitsu.fr/video-app/014.mp4"));
        localQuestionList.add(new Question(15,Level.YELLOW,Type.PRACTICE,"Clé de bras sous l'aisselle - waki gatame","","","https://www.nihon-tai-jitsu.fr/video-app/015.mp4"));
        localQuestionList.add(new Question(16,Level.YELLOW,Type.PRACTICE,"Clé de bras tendue sur l'épaule - Tembim Gatame","","","https://www.nihon-tai-jitsu.fr/video-app/016.mp4"));
        localQuestionList.add(new Question(17,Level.YELLOW,Type.PRACTICE,"Mise en garde classique","","","https://www.nihon-tai-jitsu.fr/video-app/017.mp4"));
        localQuestionList.add(new Question(18,Level.YELLOW,Type.PRACTICE,"Mise en garde garde mixte","","","https://www.nihon-tai-jitsu.fr/video-app/018.mp4"));
        localQuestionList.add(new Question(19,Level.YELLOW,Type.PRACTICE,"Mise en garde garde \"kempo\"","","","https://www.nihon-tai-jitsu.fr/video-app/019.mp4"));
        localQuestionList.add(new Question(20,Level.YELLOW,Type.PRACTICE,"Esquive en avançant (IRIMI)","","","https://www.nihon-tai-jitsu.fr/video-app/020.mp4"));
        localQuestionList.add(new Question(21,Level.YELLOW,Type.PRACTICE,"Esquive en reculant (NAGASHI)","","","https://www.nihon-tai-jitsu.fr/video-app/021.mp4"));
        localQuestionList.add(new Question(22,Level.YELLOW,Type.PRACTICE,"Esquive latérale (HIRAKI)","","","https://www.nihon-tai-jitsu.fr/video-app/022.mp4"));
        localQuestionList.add(new Question(23,Level.YELLOW,Type.PRACTICE,"Esquive rotative (IRIMI SENKAI)","","","https://www.nihon-tai-jitsu.fr/video-app/023.mp4"));
        localQuestionList.add(new Question(24,Level.YELLOW,Type.PRACTICE,"Parade basse","","","https://www.nihon-tai-jitsu.fr/video-app/024.mp4"));
        localQuestionList.add(new Question(25,Level.YELLOW,Type.PRACTICE,"Parade niveau moyen","","","https://www.nihon-tai-jitsu.fr/video-app/025.mp4"));
        localQuestionList.add(new Question(26,Level.YELLOW,Type.PRACTICE,"Parade haute","","","https://www.nihon-tai-jitsu.fr/video-app/026.mp4"));
        localQuestionList.add(new Question(27,Level.YELLOW,Type.PRACTICE,"Clé de bras en croix (juji ude gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/027.mp4"));
        localQuestionList.add(new Question(28,Level.YELLOW,Type.PRACTICE,"Clé de poignet vers l'extérieur (kote gaeshi)","","","https://www.nihon-tai-jitsu.fr/video-app/028.mp4"));
        localQuestionList.add(new Question(29,Level.YELLOW,Type.PRACTICE,"projection \"en rentrant\" (mukae daoshi ou irimi nage)","","","https://www.nihon-tai-jitsu.fr/video-app/029.mp4"));
        localQuestionList.add(new Question(30,Level.YELLOW,Type.PRACTICE,"Grand fauchage de la jambe extérieure (O soto gari)","","","https://www.nihon-tai-jitsu.fr/video-app/030.mp4"));
        localQuestionList.add(new Question(31,Level.YELLOW,Type.PRACTICE,"Changement de garde en avançant et en reculant","","","https://www.nihon-tai-jitsu.fr/video-app/031.mp4"));
        localQuestionList.add(new Question(32,Level.YELLOW,Type.PRACTICE,"Mise en YOI","","","https://www.nihon-tai-jitsu.fr/video-app/032.mp4"));
        localQuestionList.add(new Question(33,Level.YELLOW,Type.PRACTICE,"Demi tour en garde","","","https://www.nihon-tai-jitsu.fr/video-app/033.mp4"));
        localQuestionList.add(new Question(34,Level.YELLOW,Type.PRACTICE,"Chute avant roulée à droite","","","https://www.nihon-tai-jitsu.fr/video-app/034.mp4"));
        localQuestionList.add(new Question(35,Level.ORANGE,Type.PRACTICE,"Chute avant plaquée à droite","","","https://www.nihon-tai-jitsu.fr/video-app/035.mp4"));
        localQuestionList.add(new Question(36,Level.YELLOW,Type.PRACTICE,"Chute arrière roulée à droite","","","https://www.nihon-tai-jitsu.fr/video-app/036.mp4"));
        localQuestionList.add(new Question(37,Level.YELLOW,Type.PRACTICE,"Chute arrière plaquée","","","https://www.nihon-tai-jitsu.fr/video-app/037.mp4"));
        localQuestionList.add(new Question(38,Level.YELLOW,Type.THEORY,"Ordres japonais donnés par le sampai en début de cours?","Seiza / Otagaï ni rei / Senseï ni rei / shomen ni rei / Kiritsu","",""));
        localQuestionList.add(new Question(39,Level.YELLOW,Type.THEORY,"Que signifie DACHI ?","Position","",""));
        localQuestionList.add(new Question(40,Level.YELLOW,Type.PRACTICE,"Coup de pied direct (mae geri)","","","https://www.nihon-tai-jitsu.fr/video-app/040.mp4"));
        localQuestionList.add(new Question(41,Level.YELLOW,Type.PRACTICE,"Coup de poing direct en avançant (oi tsuki)","","","https://www.nihon-tai-jitsu.fr/video-app/041.mp4"));
        localQuestionList.add(new Question(42,Level.YELLOW,Type.PRACTICE,"Coup de poing sen opposition (gyaku tsuki)","","","https://www.nihon-tai-jitsu.fr/video-app/042.mp4"));
        localQuestionList.add(new Question(43,Level.YELLOW,Type.PRACTICE,"Coup de coude (empi uchi)","","","https://www.nihon-tai-jitsu.fr/video-app/043.mp4"));
        localQuestionList.add(new Question(44,Level.YELLOW,Type.PRACTICE,"Fouetté des doigts (me uchi)","","","https://www.nihon-tai-jitsu.fr/video-app/044.mp4"));
        localQuestionList.add(new Question(45,Level.YELLOW,Type.PRACTICE,"Coup de genou (hiza geri)","","","https://www.nihon-tai-jitsu.fr/video-app/045.mp4"));
        localQuestionList.add(new Question(46,Level.YELLOW,Type.PRACTICE,"Chute latérale à droite","","","https://www.nihon-tai-jitsu.fr/video-app/046.mp4"));
        localQuestionList.add(new Question(47,Level.YELLOW,Type.PRACTICE,"Garde au sol","","","https://www.nihon-tai-jitsu.fr/video-app/047.mp4"));
        localQuestionList.add(new Question(48,Level.ORANGE,Type.PRACTICE,"base 1 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/048.mp4"));
        localQuestionList.add(new Question(49,Level.YELLOW,Type.THEORY,"Que signifie TAI-SABAKI ?","Esquive","",""));
        localQuestionList.add(new Question(50,Level.ORANGE,Type.PRACTICE,"base 1 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/050.mp4"));
        localQuestionList.add(new Question(51,Level.ORANGE,Type.PRACTICE,"base 2 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/051.mp4"));
        localQuestionList.add(new Question(52,Level.ORANGE,Type.PRACTICE,"base 2 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/052.mp4"));
        localQuestionList.add(new Question(53,Level.ORANGE,Type.PRACTICE,"base 3 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/053.mp4"));
        localQuestionList.add(new Question(54,Level.YELLOW,Type.THEORY,"Que signifie TORI ?","Le défenseur, celui qui exécute la technique","",""));
        localQuestionList.add(new Question(55,Level.YELLOW,Type.THEORY,"Que signifie UKE ?","L’attaquant, celui qui la subit la technique","",""));
        localQuestionList.add(new Question(56,Level.ORANGE,Type.THEORY,"Que signifie WAZA ?","Techniques","",""));
        localQuestionList.add(new Question(57,Level.ORANGE,Type.PRACTICE,"base 3 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/057.mp4"));
        localQuestionList.add(new Question(58,Level.ORANGE,Type.PRACTICE,"base 4 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/058.mp4"));
        localQuestionList.add(new Question(59,Level.ORANGE,Type.PRACTICE,"base 4 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/059.mp4"));
        localQuestionList.add(new Question(60,Level.ORANGE,Type.PRACTICE,"base 5 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/060.mp4"));
        localQuestionList.add(new Question(61,Level.ORANGE,Type.PRACTICE,"base 5 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/061.mp4"));
        localQuestionList.add(new Question(62,Level.ORANGE,Type.PRACTICE,"base 6 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/062.mp4"));
        localQuestionList.add(new Question(63,Level.ORANGE,Type.PRACTICE,"base 6 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/063.mp4"));
        localQuestionList.add(new Question(64,Level.YELLOW,Type.THEORY,"Que signifie ATEMI ?","Coup frappé par une partie sensible du corps","",""));
        localQuestionList.add(new Question(65,Level.ORANGE,Type.PRACTICE,"base 7 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/065.mp4"));
        localQuestionList.add(new Question(66,Level.ORANGE,Type.PRACTICE,"base 7 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/066.mp4"));
        localQuestionList.add(new Question(67,Level.ORANGE,Type.PRACTICE,"base 8 par clé","","","https://www.nihon-tai-jitsu.fr/video-app/067.mp4"));
        localQuestionList.add(new Question(68,Level.ORANGE,Type.PRACTICE,"base 8 par projection","","","https://www.nihon-tai-jitsu.fr/video-app/068.mp4"));
        localQuestionList.add(new Question(69,Level.ORANGE,Type.THEORY,"Que signifie UKEMI ?","Chute","",""));
        localQuestionList.add(new Question(70,Level.ORANGE,Type.PRACTICE,"Frappe en sabre haut diagonal (yokomen uchi)","","","https://www.nihon-tai-jitsu.fr/video-app/070.mp4"));
        localQuestionList.add(new Question(71,Level.ORANGE,Type.PRACTICE,"Frappe en sabre de main haut (shomen uchi)","","","https://www.nihon-tai-jitsu.fr/video-app/071.mp4"));
        localQuestionList.add(new Question(72,Level.ORANGE,Type.PRACTICE,"Différentes formes de coup de coude (empi uchi)","Debout pieds écartés et parallèles","","https://www.nihon-tai-jitsu.fr/video-app/072.mp4"));
        localQuestionList.add(new Question(73,Level.ORANGE,Type.PRACTICE,"Coup de pied circulaire (mawashi geri)","Position du cavalier","","https://www.nihon-tai-jitsu.fr/video-app/073.mp4"));
        localQuestionList.add(new Question(74,Level.ORANGE,Type.PRACTICE,"Chute avant roulée à gauche","","","https://www.nihon-tai-jitsu.fr/video-app/074.mp4"));
        localQuestionList.add(new Question(75,Level.ORANGE,Type.PRACTICE,"Chute avant plaquée à gauche","","","https://www.nihon-tai-jitsu.fr/video-app/075.mp4"));
        localQuestionList.add(new Question(76,Level.ORANGE,Type.PRACTICE,"Chute arrière roulée à gauche","","","https://www.nihon-tai-jitsu.fr/video-app/076.mp4"));
        localQuestionList.add(new Question(77,Level.ORANGE,Type.PRACTICE,"Clé de main inversée (kote gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/077.mp4"));
        localQuestionList.add(new Question(78,Level.ORANGE,Type.PRACTICE,"Clé en enroulement (ude garami)","","","https://www.nihon-tai-jitsu.fr/video-app/078.mp4"));
        localQuestionList.add(new Question(79,Level.ORANGE,Type.PRACTICE,"Clé de poignet en torsion vers l'extérieur par le pouce (gyaku kote gaeshi)","","","https://www.nihon-tai-jitsu.fr/video-app/079.mp4"));
        localQuestionList.add(new Question(80,Level.ORANGE,Type.PRACTICE,"Clé de poignet en torsion vers l'intérieur (yuki chigae = kote hineri)","","","https://www.nihon-tai-jitsu.fr/video-app/080.mp4"));
        localQuestionList.add(new Question(81,Level.ORANGE,Type.PRACTICE,"Clé sur le pouce","","","https://www.nihon-tai-jitsu.fr/video-app/081.mp4"));
        localQuestionList.add(new Question(82,Level.ORANGE,Type.PRACTICE,"Clé de \"bras en Z\" (kote kudaki)","","","https://www.nihon-tai-jitsu.fr/video-app/082.mp4"));
        localQuestionList.add(new Question(83,Level.ORANGE,Type.PRACTICE,"Crochetage de jambe intérieure – Ko uchi gari","","","https://www.nihon-tai-jitsu.fr/video-app/083.mp4"));
        localQuestionList.add(new Question(84,Level.ORANGE,Type.PRACTICE,"Chargement de hanche – O goshi","","","https://www.nihon-tai-jitsu.fr/video-app/084.mp4"));
        localQuestionList.add(new Question(85,Level.ORANGE,Type.PRACTICE,"Chargement au cou – kubi nage","","","https://www.nihon-tai-jitsu.fr/video-app/085.mp4"));
        localQuestionList.add(new Question(86,Level.ORANGE,Type.PRACTICE,"Chargement d’épaule à l’intérieur – ippon seoi nage","","","https://www.nihon-tai-jitsu.fr/video-app/086.mp4"));
        localQuestionList.add(new Question(87,Level.ORANGE,Type.PRACTICE,"Ramassement de jambes – do gaeshi","","","https://www.nihon-tai-jitsu.fr/video-app/087.mp4"));
        localQuestionList.add(new Question(88,Level.ORANGE,Type.PRACTICE,"Projection par la tête – hachi mawashi","","","https://www.nihon-tai-jitsu.fr/video-app/088.mp4"));
        localQuestionList.add(new Question(89,Level.ORANGE,Type.PRACTICE,"Projection par le bras enroulé – shiho nage","","","https://www.nihon-tai-jitsu.fr/video-app/089.mp4"));
        localQuestionList.add(new Question(90,Level.ORANGE,Type.PRACTICE,"Projection par l’épaule à l’extérieur –(tembim nage ou ude kakae)","","","https://www.nihon-tai-jitsu.fr/video-app/090.mp4"));
        localQuestionList.add(new Question(91,Level.ORANGE,Type.PRACTICE,"chute lattérale plaquée à gauche","","",""));
        localQuestionList.add(new Question(92,Level.GREEN,Type.PRACTICE,"Ushiro kata otoshi","","","https://www.nihon-tai-jitsu.fr/video-app/092.mp4"));
        localQuestionList.add(new Question(93,Level.GREEN,Type.PRACTICE,"Clé de bras en flexion sur l’épaule (début de shiho nage)","","",""));
        localQuestionList.add(new Question(94,Level.GREEN,Type.PRACTICE,"Clé de bras en flexion dans le dos (ude gatame)","","",""));
        localQuestionList.add(new Question(95,Level.GREEN,Type.PRACTICE,"Clé sur le bras tendu (ude gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/095.mp4"));
        localQuestionList.add(new Question(96,Level.GREEN,Type.PRACTICE,"Clé de bras en enroulement (2 formes) – ude garami","","","https://www.nihon-tai-jitsu.fr/video-app/096.mp4"));
        localQuestionList.add(new Question(97,Level.GREEN,Type.PRACTICE,"Clé sur le ventre – Hara gatame","","","https://www.nihon-tai-jitsu.fr/video-app/097.mp4"));
        localQuestionList.add(new Question(98,Level.GREEN,Type.PRACTICE,"Balayage de pied – de hachi barai","","","https://www.nihon-tai-jitsu.fr/video-app/098.mp4"));
        localQuestionList.add(new Question(99,Level.GREEN,Type.PRACTICE,"Sutemi avant – tomoe nage","","","https://www.nihon-tai-jitsu.fr/video-app/099.mp4"));
        localQuestionList.add(new Question(100,Level.GREEN,Type.PRACTICE,"Sutemi arrière (tani otoshi)","","","https://www.nihon-tai-jitsu.fr/video-app/100.mp4"));
        localQuestionList.add(new Question(101,Level.GREEN,Type.PRACTICE,"Sutemi arrière en ciseau de jambe (kami basami)","","","https://www.nihon-tai-jitsu.fr/video-app/101.mp4"));
        localQuestionList.add(new Question(102,Level.GREEN,Type.PRACTICE,"Projection en renversement arrière (do gaeshi)","","",""));
        localQuestionList.add(new Question(103,Level.GREEN,Type.PRACTICE,"Sutemi latéral – yoko sutemi","","","https://www.nihon-tai-jitsu.fr/video-app/103.mp4"));
        localQuestionList.add(new Question(104,Level.GREEN,Type.PRACTICE,"Chargement sur les deux épaules (debout) – kata Guruma","","","https://www.nihon-tai-jitsu.fr/video-app/104.mp4"));
        localQuestionList.add(new Question(105,Level.GREEN,Type.PRACTICE,"Chargement sur les deux épaules (un genou au sol) – Tamara Guruma","","","https://www.nihon-tai-jitsu.fr/video-app/105.mp4"));
        localQuestionList.add(new Question(106,Level.GREEN,Type.PRACTICE,"Immobilisation par clé de poignet bras tendu, uke sur le dos (tâte gassho gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/106.mp4"));
        localQuestionList.add(new Question(107,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en torsion de main, uke sur le dos (kote gaeshi gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/107.mp4"));
        localQuestionList.add(new Question(108,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en flexion de bras, uke sur le dos (ude garami)","","","https://www.nihon-tai-jitsu.fr/video-app/108.mp4"));
        localQuestionList.add(new Question(109,Level.GREEN,Type.PRACTICE,"Immobilisation par clé sur le poignet, uke sur le ventre (kote gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/109.mp4"));
        localQuestionList.add(new Question(110,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en triangle, uke sur le ventre (sankaku gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/110.mp4"));
        localQuestionList.add(new Question(111,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en pression au coude (uke sur le ventre)","","","https://www.nihon-tai-jitsu.fr/video-app/111.mp4"));
        localQuestionList.add(new Question(112,Level.GREEN,Type.PRACTICE,"Renversement du corps en barrage (harai goshi)","","","https://www.nihon-tai-jitsu.fr/video-app/112.mp4"));
        localQuestionList.add(new Question(113,Level.GREEN,Type.PRACTICE,"Atemi à quatre phalanges - hiraken","","","https://www.nihon-tai-jitsu.fr/video-app/113.mp4"));
        localQuestionList.add(new Question(114,Level.GREEN,Type.PRACTICE,"Kakato geri","","","https://www.nihon-tai-jitsu.fr/video-app/114.mp4"));
        localQuestionList.add(new Question(115,Level.BROWN,Type.PRACTICE,"Etranglement à main nu (hadaka jime)","","",""));
        localQuestionList.add(new Question(116,Level.GREEN,Type.PRACTICE,"Projection par rammassement de jambe (sukui nage)","","","https://www.nihon-tai-jitsu.fr/video-app/116.mp4"));
        localQuestionList.add(new Question(117,Level.GREEN,Type.PRACTICE,"Projection de la \"rame\" (robuse ou ude osae)","","","https://www.nihon-tai-jitsu.fr/video-app/117.mp4"));
        localQuestionList.add(new Question(118,Level.GREEN,Type.PRACTICE,"Coup de pied latéral - Yoko Geri","","","https://www.nihon-tai-jitsu.fr/video-app/118.mp4"));
        localQuestionList.add(new Question(119,Level.BROWN,Type.THEORY,"Qu'est ce qu'un KIHON IPPON KUMITE ?","Assaut conventionnel: une attaque contrée par une seule défense et ou contre-attaque","",""));
        localQuestionList.add(new Question(120,Level.ORANGE,Type.THEORY,"Que signifie RITSUREI ?","Salut debout","",""));
        localQuestionList.add(new Question(121,Level.ORANGE,Type.THEORY,"Que signifie ZAREI ?","Salut à genoux","",""));
        localQuestionList.add(new Question(122,Level.GREEN,Type.THEORY,"Que signifie REISHIKI ?","Cérémonial","",""));
        localQuestionList.add(new Question(123,Level.YELLOW,Type.THEORY,"Que signifie SEIZA ?","Position à genoux","",""));
        localQuestionList.add(new Question(124,Level.YELLOW,Type.THEORY,"Qu'exprime le mot YAME ?","Fin de l’exercice retour en YOI (toujours vigilent)","",""));
        localQuestionList.add(new Question(125,Level.YELLOW,Type.THEORY,"Qu'exprime le mot YASSME ?","Se décontracter (intervient YAME)","",""));
        localQuestionList.add(new Question(126,Level.YELLOW,Type.THEORY,"Qu'exprime le mot YOI ?","Position et état mental concentré et vigilant","",""));
        localQuestionList.add(new Question(127,Level.YELLOW,Type.THEORY,"Que signifie KIMONO ?","littéralement \"chose que l'on porte sur soi\".","",""));
        localQuestionList.add(new Question(128,Level.YELLOW,Type.THEORY,"Que signifie KEIKO GI ?","Vêtement d’entraînement\"","",""));
        localQuestionList.add(new Question(129,Level.GREEN,Type.PRACTICE,"Yama arashi","","","https://www.nihon-tai-jitsu.fr/video-app/129.mp4"));
        localQuestionList.add(new Question(130,Level.BLUE,Type.PRACTICE,"Technique de \"viens donc\" par clé en bec de cygne","","","https://www.nihon-tai-jitsu.fr/video-app/130.mp4"));
        localQuestionList.add(new Question(131,Level.BLUE,Type.PRACTICE,"Technique de \"viens donc\" par le pouce","","","https://www.nihon-tai-jitsu.fr/video-app/131.mp4"));
        localQuestionList.add(new Question(132,Level.BLUE,Type.PRACTICE,"Technique de \"viens donc\" par clé de bras en croix","","","https://www.nihon-tai-jitsu.fr/video-app/132.mp4"));
        localQuestionList.add(new Question(133,Level.BLUE,Type.PRACTICE,"étranglement nu (hadaka jime)","","","https://www.nihon-tai-jitsu.fr/video-app/133.mp4"));
        localQuestionList.add(new Question(134,Level.BLUE,Type.PRACTICE,"étranglement en enroulant par la manche (sode guruma jime)","","","https://www.nihon-tai-jitsu.fr/video-app/134.mp4"));
        localQuestionList.add(new Question(135,Level.BLUE,Type.PRACTICE,"étranglement en contrôlant l'épaule (kata ha jime)","","","https://www.nihon-tai-jitsu.fr/video-app/135.mp4"));
        localQuestionList.add(new Question(136,Level.BLUE,Type.PRACTICE,"étranglement avec le revers (eri jime)","","","https://www.nihon-tai-jitsu.fr/video-app/136.mp4"));
        localQuestionList.add(new Question(137,Level.BLUE,Type.PRACTICE,"Coup de pied direct sauté (Mae Tobi Geri)","","","https://www.nihon-tai-jitsu.fr/video-app/137.mp4"));
        localQuestionList.add(new Question(138,Level.BLUE,Type.PRACTICE,"Coup de pied en ruade (Ushiro Geri)","","","https://www.nihon-tai-jitsu.fr/video-app/138.mp4"));
        localQuestionList.add(new Question(139,Level.BLUE,Type.PRACTICE,"Coup de pied circulaire retourné - Ushiro Ura Mawashi Geri","","","https://www.nihon-tai-jitsu.fr/video-app/139.mp4"));
        localQuestionList.add(new Question(140,Level.BLUE,Type.PRACTICE,"Immobilisation par clé de cheville en croix, uke sur le dos (kata ashi hishigi)","","","https://www.nihon-tai-jitsu.fr/video-app/140.mp4"));
        localQuestionList.add(new Question(141,Level.BLUE,Type.PRACTICE,"Immobilisation par clé de jambe fléchie, uke sur le ventre (hiza hishigi)","","","https://www.nihon-tai-jitsu.fr/video-app/141.mp4"));
        localQuestionList.add(new Question(142,Level.BLUE,Type.PRACTICE,"Immobilisation par clé de cheville en torsion vers l'intérieur, uke sur le dos (ashi dori garami)","","","https://www.nihon-tai-jitsu.fr/video-app/142.mp4"));
        localQuestionList.add(new Question(143,Level.ORANGE,Type.PRACTICE,"Garde un genou au au sol","","","https://www.nihon-tai-jitsu.fr/video-app/143.mp4"));
        localQuestionList.add(new Question(144,Level.BLUE,Type.PRACTICE,"Sol: Tori au sol, uke debout devant (plusieurs formes d’atemi et de ciseaux)","","",""));
        localQuestionList.add(new Question(145,Level.BLUE,Type.PRACTICE,"Sol: Uke à cheval sur Tori","","",""));
        localQuestionList.add(new Question(146,Level.BLUE,Type.PRACTICE,"Sol: Uke attaque entre les jambes de Tori","","",""));
        localQuestionList.add(new Question(147,Level.BLUE,Type.PRACTICE,"Sol: Uke est derrière Tori","","",""));
        localQuestionList.add(new Question(148,Level.BLUE,Type.PRACTICE,"Sol: Tori renverse Uke","","",""));
        localQuestionList.add(new Question(149,Level.BLUE,Type.PRACTICE,"Crochet (Mawashi Tsuki)","","","https://www.nihon-tai-jitsu.fr/video-app/149.mp4"));
        localQuestionList.add(new Question(150,Level.BLUE,Type.PRACTICE,"Upercut","","","https://www.nihon-tai-jitsu.fr/video-app/150.mp4"));
        localQuestionList.add(new Question(151,Level.BROWN,Type.PRACTICE,"projection extérieure (soto tenkai)","","","https://www.nihon-tai-jitsu.fr/video-app/151.mp4"));
        localQuestionList.add(new Question(152,Level.BROWN,Type.PRACTICE,"projection par le col et par l'arrière (ushiro eri kata nage)","","","https://www.nihon-tai-jitsu.fr/video-app/152.mp4"));
        localQuestionList.add(new Question(153,Level.BROWN,Type.PRACTICE,"projection lattérale par poussée du coude (ude oshiage)","","","https://www.nihon-tai-jitsu.fr/video-app/153.mp4"));
        localQuestionList.add(new Question(154,Level.BROWN,Type.PRACTICE,"projection arrière en poussée du menton (ago ate nage)","","","https://www.nihon-tai-jitsu.fr/video-app/154.mp4"));
        localQuestionList.add(new Question(155,Level.BLACK,Type.PRACTICE,"projection par traction de la manche (uki otoshi)","","",""));
        localQuestionList.add(new Question(156,Level.BLACK,Type.PRACTICE,"projection par traction de la manche et du cou (kubi uki otoshi)","","",""));
        localQuestionList.add(new Question(157,Level.BLACK,Type.PRACTICE,"sutemi par le cou (kubi nage sutemi)","","",""));
        localQuestionList.add(new Question(158,Level.BLACK,Type.PRACTICE,"projection par par levier de l'avant bras au genou (ashi otoshi)","","","https://www.nihon-tai-jitsu.fr/video-app/158.mp4"));
        localQuestionList.add(new Question(159,Level.BLACK,Type.PRACTICE,"projection par fauchage bras tendu (ude maki soto gari)","","","https://www.nihon-tai-jitsu.fr/video-app/159.mp4"));
        localQuestionList.add(new Question(160,Level.BLACK,Type.PRACTICE,"sutemi (tobi nori sutemi)","","",""));
        localQuestionList.add(new Question(161,Level.BLACK,Type.PRACTICE,"projection enroulée par la manche (sode guruma otoshi)","","",""));
        localQuestionList.add(new Question(162,Level.BLACK,Type.PRACTICE,"Yoko sutemi (yoko otoshi)","","",""));
        localQuestionList.add(new Question(163,Level.BLACK,Type.PRACTICE,"sutemi dans l'angle (sumi gaeshi)","","",""));
        localQuestionList.add(new Question(164,Level.BLACK,Type.PRACTICE,"kubi mawashi sutemi","","",""));
        localQuestionList.add(new Question(165,Level.BLACK,Type.PRACTICE,"yoko guruma","","","https://www.nihon-tai-jitsu.fr/video-app/165.mp4"));
        localQuestionList.add(new Question(166,Level.BLACK,Type.PRACTICE,"ude gake sutemi","","",""));
        localQuestionList.add(new Question(167,Level.BROWN,Type.THEORY,"Que signifie Kamiza?","Place d'honneur","",""));
        localQuestionList.add(new Question(168,Level.BROWN,Type.THEORY,"Qu'est-ce qu'un Kuatsu?","Une technique de soin ou de réanimation par percussion de zone particulères du corps","",""));
        localQuestionList.add(new Question(169,Level.BLACK,Type.PRACTICE,"eri tori sutemi","","",""));
        localQuestionList.add(new Question(170,Level.BLACK,Type.PRACTICE,"Yoko tomoe","","",""));
        localQuestionList.add(new Question(171,Level.BLACK,Type.PRACTICE,"atama guruma","","",""));
        localQuestionList.add(new Question(172,Level.YELLOW,Type.THEORY,"Que signifie KAMAE ?","En garde !","",""));
        localQuestionList.add(new Question(173,Level.ORANGE,Type.THEORY,"Quels sont les ordres japonais donnés par le sampai en fin de cours?","Seiza / Shomen ni rei / Senseï ni rei / Otagaï ni rei / Kiritsu","",""));
        localQuestionList.add(new Question(174,Level.YELLOW,Type.THEORY,"Que signifie Yoi ?","Position de vigilance physique et mentale et un ordre donné par le professeur de prendre la position Yoi","",""));
        localQuestionList.add(new Question(175,Level.YELLOW,Type.THEORY,"Que signifie SEIZA ?","Position agenouillée et ordre donné par le professeur de se mettre à genou","",""));
        localQuestionList.add(new Question(176,Level.ORANGE,Type.THEORY,"Que signifie \"Senseï ni rei\"","Saluez le professeur (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        localQuestionList.add(new Question(177,Level.ORANGE,Type.THEORY,"Que signifie \"Otagaï ni rei\"","Saluez-vous entre élèves (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        localQuestionList.add(new Question(178,Level.ORANGE,Type.THEORY,"Que signifie \"Shomen ni rei\"","Saluez la mémoire des ancêtres (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        localQuestionList.add(new Question(179,Level.YELLOW,Type.THEORY,"Que signifie Kiritsu","Relevez-vous (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        localQuestionList.add(new Question(180,Level.YELLOW,Type.THEORY,"Que signifie Hajime","Commencez (l'exercice, le combat…)","",""));
        localQuestionList.add(new Question(181,Level.YELLOW,Type.THEORY,"Que signifie Matte","Arrêtez-vous (ordre très important)","",""));
        localQuestionList.add(new Question(182,Level.GREEN,Type.THEORY,"Que signifie ONEGAISHIMASU","\"s’il vous plait\" formule de politesse \"Voulez-vous travailler avec moi ? SVP\"","",""));
        localQuestionList.add(new Question(183,Level.GREEN,Type.THEORY,"Que signifie ARIGATO GOZAIMASU","merci beaucoup (pour vos conseils, pour avoir travaillé avec moi, pour le cours, etc…)","",""));
        localQuestionList.add(new Question(184,Level.YELLOW,Type.THEORY,"Que signifie ICHI, NI, SAN, SHI (YON), GO, ROKU, SHICHI (NANA), ACHI, KYU, JUU ?","Compte de 1 à 10","",""));
        localQuestionList.add(new Question(185,Level.YELLOW,Type.THEORY,"Que signifie REI ?","Saluez","",""));
        localQuestionList.add(new Question(190,Level.ORANGE,Type.PRACTICE,"Parade à deux niveaux","","","https://www.nihon-tai-jitsu.fr/video-app/190.mp4"));
        localQuestionList.add(new Question(191,Level.BLUE,Type.PRACTICE,"Technique de \"viens donc\" par clé et étranglement","","","https://www.nihon-tai-jitsu.fr/video-app/191.mp4"));
        localQuestionList.add(new Question(192,Level.BLUE,Type.PRACTICE,"Coup de pied sauté circulaire (Mawashi Tobi Geri)","","","https://www.nihon-tai-jitsu.fr/video-app/192.mp4"));
        localQuestionList.add(new Question(193,Level.ORANGE,Type.PRACTICE,"Ryote Ippon Seoi Nage","","","https://www.nihon-tai-jitsu.fr/video-app/193.mp4"));
        localQuestionList.add(new Question(194,Level.BLACK,Type.PRACTICE,"Yoko wakare","","","https://www.nihon-tai-jitsu.fr/video-app/194.mp4"));
        localQuestionList.add(new Question(195,Level.BLACK,Type.PRACTICE,"Sutemi arrière par les hanches","","","https://www.nihon-tai-jitsu.fr/video-app/195.mp4"));
        localQuestionList.add(new Question(196,Level.GREEN,Type.PRACTICE,"Immobilisation par clé de bras en croix (ude juji gatame)","","","https://www.nihon-tai-jitsu.fr/video-app/196.mp4"));
        localQuestionList.add(new Question(197,Level.GREEN,Type.PRACTICE,"Juji gatame","","","https://www.nihon-tai-jitsu.fr/video-app/197.mp4"));
        localQuestionList.add(new Question(198,Level.GREEN,Type.PRACTICE,"immobilisation par Ude Garami","","","https://www.nihon-tai-jitsu.fr/video-app/198.mp4"));
        localQuestionList.add(new Question(199,Level.GREEN,Type.PRACTICE,"Différentes formes de clé de pouce","","",""));
        localQuestionList.add(new Question(200,Level.ORANGE,Type.PRACTICE,"Nekko ahshi dachi","","","https://www.nihon-tai-jitsu.fr/video-app/200.mp4"));
        localQuestionList.add(new Question(201,Level.ORANGE,Type.PRACTICE,"Marteau de fer - tetsui","","","https://www.nihon-tai-jitsu.fr/video-app/201.mp4"));
        localQuestionList.add(new Question(202,Level.BLUE,Type.PRACTICE,"Coup de pied circulaire inversé - Ura Mawashi Geri","","","https://www.nihon-tai-jitsu.fr/video-app/202.mp4"));
        localQuestionList.add(new Question(203,Level.ORANGE,Type.PRACTICE,"Frappe en revers de poing (uraken uchi)","","","https://www.nihon-tai-jitsu.fr/video-app/203.mp4"));
        localQuestionList.add(new Question(204,Level.YELLOW,Type.PRACTICE,"Kihon waza - déplacements seuls","","","https://www.nihon-tai-jitsu.fr/video-app/204.mp4"));
        localQuestionList.add(new Question(205,Level.YELLOW,Type.PRACTICE,"Kihon waza - déplacements et parades","","","https://www.nihon-tai-jitsu.fr/video-app/205.mp4"));
        localQuestionList.add(new Question(206,Level.ORANGE,Type.PRACTICE,"Kihon waza - déplacements et parades à deux","","","https://www.nihon-tai-jitsu.fr/video-app/206.mp4"));

        return localQuestionList;
    }

    public String getInitDescription() {
        return initDescription;
    }

    public interface Initiator {
        void loaded(Database database);
    }

}
