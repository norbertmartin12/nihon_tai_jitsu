package org.ntj_workout.data;

import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class Database {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private List<Question> questionList;
    private String initDescription;

    public Database init(Initiator initiator, ConnectivityManager connectivityManager) {
        loadQuestionList(initiator, connectivityManager);
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

    private void loadQuestionList(final Initiator initiator, ConnectivityManager connectivityManager) {
        if (this.questionList != null && !this.questionList.isEmpty()) {
            initiator.loaded(this);
            return;
        }

        final boolean isActiveNetworkNonMetered = !connectivityManager.isActiveNetworkMetered();
        EXECUTOR_SERVICE.submit(() -> {
            List<Question> list = null;
            if (isActiveNetworkNonMetered) {
                list = parseJsonQuestionList(getOnlineQuestionJson());
                initDescription = "online - " + list.size() + " questions";
            }
            if (list == null || list.isEmpty()) {
                list = getOfflineQuestionList();
                initDescription = "offline - " + list.size() + " questions";
            }
            this.questionList = Collections.unmodifiableList(list);
            initiator.loaded(this);
        });
    }

    private static JSONObject getOnlineQuestionJson() {
        String sheetsUrl = "https://sheets.googleapis.com/v4/spreadsheets/1ymOsztjiYsp3w9OS4XdOGtqcJQyHXiRPpR8jmrWTFOg/values/human?alt=json&key=AIzaSyBO4GJYpO_kRby3sle3HE10u3967eJN0KA";
        HttpsURLConnection urlConnection = null;
        JSONObject jsonObject = null;
        try {
            urlConnection = (HttpsURLConnection) new URL(sheetsUrl).openConnection();
            urlConnection.setConnectTimeout(500);
            urlConnection.setReadTimeout(500);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.connect();
            jsonObject = new JSONObject(new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).lines().collect(Collectors.joining()));
        } catch (IOException | JSONException e) {
            Log.e("data_pull", e.getLocalizedMessage());
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
            Log.e("JSONException", e.getLocalizedMessage());
        }
        Log.i("sheets_pulling", "contains " + loadedQuestionList.size() + " questions");
        return loadedQuestionList;
    }

    private static List<Question> getOfflineQuestionList() {
        //"https://drive.google.com/uc?export=view&id="
        List<Question> localQuestionList = new LinkedList<>();
        localQuestionList.add(new Question(1, Level.YELLOW, Type.PRACTICE, "Base 1 par atemi", "", "", ""));
        localQuestionList.add(new Question(2, Level.YELLOW, Type.PRACTICE, "Base 2 par atemi", "", "", ""));
        localQuestionList.add(new Question(3, Level.YELLOW, Type.PRACTICE, "Base 3 par atemi  (deux formes)", "", "", ""));
        localQuestionList.add(new Question(4, Level.YELLOW, Type.PRACTICE, "Base 4 par atemi (deux formes)", "", "", ""));
        localQuestionList.add(new Question(5, Level.YELLOW, Type.PRACTICE, "Base 5 par atemi", "", "", ""));
        localQuestionList.add(new Question(6, Level.YELLOW, Type.PRACTICE, "Base 6 par atemi", "", "", ""));
        localQuestionList.add(new Question(7, Level.YELLOW, Type.PRACTICE, "Base 7 par atemi", "", "", ""));
        localQuestionList.add(new Question(8, Level.YELLOW, Type.PRACTICE, "Base 8 par atemi ( (deux formes)", "", "", ""));
        localQuestionList.add(new Question(9, Level.YELLOW, Type.PRACTICE, "Faire son noeud de ceinture", "", "", "https://youtube.com/shorts/1tJwxh5NRWw"));
        localQuestionList.add(new Question(10, Level.YELLOW, Type.PRACTICE, "Salut debout", "", "", "https://youtube.com/shorts/a4tf8D4PS3g"));
        localQuestionList.add(new Question(11, Level.YELLOW, Type.PRACTICE, "Position ZENKUTSU DACHI", "", "", "https://youtube.com/shorts/z9WK-N7Fk94"));
        localQuestionList.add(new Question(12, Level.YELLOW, Type.PRACTICE, "Position KOKUTSU DACHI", "", "", ""));
        localQuestionList.add(new Question(13, Level.YELLOW, Type.PRACTICE, "Position SHIKO DACHI", "", "", "https://youtu.be/fLacl3wm-lw"));
        localQuestionList.add(new Question(14, Level.YELLOW, Type.PRACTICE, "Salut à genou", "", "", "https://youtube.com/shorts/1mf8sh3Oh-w"));
        localQuestionList.add(new Question(15, Level.YELLOW, Type.PRACTICE, "Clé de bras sous l'aisselle - waki gatame", "", "", "https://youtube.com/shorts/XfH_qxQHZwY"));
        localQuestionList.add(new Question(16, Level.YELLOW, Type.PRACTICE, "Clé de bras tendue sur l'épaule - Tembim Gatame", "", "", "https://youtube.com/shorts/Hnm6xD6r_6w"));
        localQuestionList.add(new Question(17, Level.YELLOW, Type.PRACTICE, "Mise en garde classique", "", "", "https://youtube.com/shorts/DvgO0KjN9DM"));
        localQuestionList.add(new Question(18, Level.YELLOW, Type.PRACTICE, "Mise en garde garde mixte", "", "", "https://youtube.com/shorts/X7DfwTBnui8"));
        localQuestionList.add(new Question(19, Level.YELLOW, Type.PRACTICE, "Mise en garde garde « kempo »", "", "", "https://youtube.com/shorts/K8ql_UtPKls"));
        localQuestionList.add(new Question(20, Level.YELLOW, Type.PRACTICE, "Esquive en avançant (IRIMI)", "", "", ""));
        localQuestionList.add(new Question(21, Level.YELLOW, Type.PRACTICE, "Esquive en reculant (NAGASHI)", "", "", ""));
        localQuestionList.add(new Question(22, Level.YELLOW, Type.PRACTICE, "Esquive latérale (HIRAKI)", "", "", ""));
        localQuestionList.add(new Question(23, Level.YELLOW, Type.PRACTICE, "Esquive rotative (IRIMI SENKAI)", "", "", ""));
        localQuestionList.add(new Question(24, Level.YELLOW, Type.PRACTICE, "Parade basse", "", "", ""));
        localQuestionList.add(new Question(25, Level.YELLOW, Type.PRACTICE, "Parade niveau moyen", "", "", ""));
        localQuestionList.add(new Question(26, Level.YELLOW, Type.PRACTICE, "Parade haute", "", "", ""));
        localQuestionList.add(new Question(27, Level.YELLOW, Type.PRACTICE, "Clé de bras en croix (juji ude gatame)", "", "", ""));
        localQuestionList.add(new Question(28, Level.YELLOW, Type.PRACTICE, "Clé de poignet vers l'extérieur (kote gaeshi)", "", "", ""));
        localQuestionList.add(new Question(29, Level.YELLOW, Type.PRACTICE, "projection 'en rentrant' (mukae daoshi ou irimi nage)", "", "", ""));
        localQuestionList.add(new Question(30, Level.YELLOW, Type.PRACTICE, "Grand fauchage de la jambe extérieure (O soto gari)", "", "", ""));
        localQuestionList.add(new Question(31, Level.YELLOW, Type.PRACTICE, "Changement de garde en avançant et en reculant", "", "", ""));
        localQuestionList.add(new Question(32, Level.YELLOW, Type.PRACTICE, "Mise en YOI", "", "https://ecolekaratemarais.fr/wp-content/uploads/2020/05/Yoi.jpg", ""));
        localQuestionList.add(new Question(33, Level.YELLOW, Type.PRACTICE, "Demi tour en garde", "", "", ""));
        localQuestionList.add(new Question(34, Level.YELLOW, Type.PRACTICE, "Chute avant roulée à droite", "", "", ""));
        localQuestionList.add(new Question(35, Level.ORANGE, Type.PRACTICE, "Chute avant plaquée à droite", "", "", ""));
        localQuestionList.add(new Question(36, Level.YELLOW, Type.PRACTICE, "Chute arrière roulée à droite", "", "", ""));
        localQuestionList.add(new Question(37, Level.YELLOW, Type.PRACTICE, "Chute arrière plaquée", "", "", ""));
        localQuestionList.add(new Question(38, Level.YELLOW, Type.THEORY, "Ordres japonais donnés par le sampai en début de cours?", "Seiza / Otagaï ni rei / Senseï ni rei / shomen ni rei / Kiritsu", "", ""));
        localQuestionList.add(new Question(39, Level.YELLOW, Type.THEORY, "Que signifie DACHI ?", "Position", "", ""));
        localQuestionList.add(new Question(40, Level.YELLOW, Type.PRACTICE, "Coup de pied direct (mae geri)", "", "", ""));
        localQuestionList.add(new Question(41, Level.YELLOW, Type.PRACTICE, "Coup de poing direct en avançant (oi tsuki)", "", "", ""));
        localQuestionList.add(new Question(42, Level.YELLOW, Type.PRACTICE, "Coup de poing sen opposition (gyaku tsuki)", "", "", ""));
        localQuestionList.add(new Question(43, Level.YELLOW, Type.PRACTICE, "Coup de coude (empi uchi)", "", "", ""));
        localQuestionList.add(new Question(44, Level.YELLOW, Type.PRACTICE, "Fouetté des doigts (me uchi)", "", "", ""));
        localQuestionList.add(new Question(45, Level.YELLOW, Type.PRACTICE, "Coup de genou (hiza geri)", "", "", ""));
        localQuestionList.add(new Question(46, Level.YELLOW, Type.PRACTICE, "Chute latérale à droite", "", "", ""));
        localQuestionList.add(new Question(47, Level.YELLOW, Type.PRACTICE, "Garde au sol", "", "", ""));
        localQuestionList.add(new Question(48, Level.ORANGE, Type.PRACTICE, "base 1 par clé", "", "", ""));
        localQuestionList.add(new Question(49, Level.YELLOW, Type.THEORY, "Que signifie TAI-SABAKI ?", "Esquive", "", ""));
        localQuestionList.add(new Question(50, Level.ORANGE, Type.PRACTICE, "base 1 par projection", "", "", ""));
        localQuestionList.add(new Question(51, Level.ORANGE, Type.PRACTICE, "base 2 par clé", "", "", ""));
        localQuestionList.add(new Question(52, Level.ORANGE, Type.PRACTICE, "base 2 par projection", "", "", ""));
        localQuestionList.add(new Question(53, Level.ORANGE, Type.PRACTICE, "base 3 par clé", "", "", ""));
        localQuestionList.add(new Question(54, Level.YELLOW, Type.THEORY, "Que signifie TORI ?", "Le défenseur, celui qui exécute la technique", "", ""));
        localQuestionList.add(new Question(55, Level.YELLOW, Type.THEORY, "Que signifie UKE ?", "L’attaquant, celui qui la subit la technique", "", ""));
        localQuestionList.add(new Question(56, Level.ORANGE, Type.THEORY, "Que signifie WAZA ?", "Techniques", "", ""));
        localQuestionList.add(new Question(57, Level.ORANGE, Type.PRACTICE, "base 3 par projection", "", "", ""));
        localQuestionList.add(new Question(58, Level.ORANGE, Type.PRACTICE, "base 4 par clé", "", "", ""));
        localQuestionList.add(new Question(59, Level.ORANGE, Type.PRACTICE, "base 4 par projection", "", "", ""));
        localQuestionList.add(new Question(60, Level.ORANGE, Type.PRACTICE, "base 5 par clé", "", "", ""));
        localQuestionList.add(new Question(61, Level.ORANGE, Type.PRACTICE, "base 5 par projection", "", "", ""));
        localQuestionList.add(new Question(62, Level.ORANGE, Type.PRACTICE, "base 6 par clé", "", "", ""));
        localQuestionList.add(new Question(63, Level.ORANGE, Type.PRACTICE, "base 6 par projection", "", "", ""));
        localQuestionList.add(new Question(64, Level.YELLOW, Type.THEORY, "Que signifie ATEMI ?", "Coup frappé par une partie sensible du corps", "", ""));
        localQuestionList.add(new Question(65, Level.ORANGE, Type.PRACTICE, "base 7 par clé", "", "", ""));
        localQuestionList.add(new Question(66, Level.ORANGE, Type.PRACTICE, "base 7 par projection", "", "", ""));
        localQuestionList.add(new Question(67, Level.ORANGE, Type.PRACTICE, "base 8 par clé", "", "", ""));
        localQuestionList.add(new Question(68, Level.ORANGE, Type.PRACTICE, "base 8 par projection", "", "", ""));
        localQuestionList.add(new Question(69, Level.ORANGE, Type.THEORY, "Que signifie UKEMI ?", "Chute", "", ""));
        localQuestionList.add(new Question(70, Level.ORANGE, Type.PRACTICE, "Frappe en sabre haut diagonal (yokomen uchi)", "", "", ""));
        localQuestionList.add(new Question(71, Level.ORANGE, Type.PRACTICE, "Frappe en sabre de main haut (shomen  uchi)", "", "", ""));
        localQuestionList.add(new Question(72, Level.ORANGE, Type.PRACTICE, "Différentes formes de coup de coude (empi uchi)", "Debout pieds écartés et parallèles", "", ""));
        localQuestionList.add(new Question(73, Level.ORANGE, Type.PRACTICE, "Coup de pied circulaire (mawashi geri)", "Position du cavalier", "", ""));
        localQuestionList.add(new Question(74, Level.ORANGE, Type.PRACTICE, "Chute avant roulée à gauche", "", "", ""));
        localQuestionList.add(new Question(75, Level.ORANGE, Type.PRACTICE, "Chute avant plaquée à gauche", "", "", ""));
        localQuestionList.add(new Question(76, Level.ORANGE, Type.PRACTICE, "Chute arrière roulée à gauche", "", "", ""));
        localQuestionList.add(new Question(77, Level.ORANGE, Type.PRACTICE, "Clé de main inversée (kote gatame)", "", "", ""));
        localQuestionList.add(new Question(78, Level.ORANGE, Type.PRACTICE, "Clé en enroulement (ude garami)", "", "", ""));
        localQuestionList.add(new Question(79, Level.ORANGE, Type.PRACTICE, "Clé de poignet en torsion vers l'extérieur par le pouce (gyaku kote gaeshi)", "", "", ""));
        localQuestionList.add(new Question(80, Level.ORANGE, Type.PRACTICE, "Clé de poignet en torsion vers l'intérieur (yuki chigae = kote hineri)", "", "", ""));
        localQuestionList.add(new Question(81, Level.ORANGE, Type.PRACTICE, "Clé sur le pouce", "", "", ""));
        localQuestionList.add(new Question(82, Level.ORANGE, Type.PRACTICE, "Clé de 'bras en Z' (kote kudaki)", "", "", ""));
        localQuestionList.add(new Question(83, Level.ORANGE, Type.PRACTICE, "Crochetage de jambe intérieure – Ko uchi gari", "", "", ""));
        localQuestionList.add(new Question(84, Level.ORANGE, Type.PRACTICE, "Chargement de hanche – O goshi", "", "", ""));
        localQuestionList.add(new Question(85, Level.ORANGE, Type.PRACTICE, "Chargement au cou – kubi nage", "", "", ""));
        localQuestionList.add(new Question(86, Level.ORANGE, Type.PRACTICE, "Chargement d’épaule à l’intérieur – ippon seoi nage", "", "", ""));
        localQuestionList.add(new Question(87, Level.ORANGE, Type.PRACTICE, "Ramassement de jambes – do gaeshi", "", "", ""));
        localQuestionList.add(new Question(88, Level.ORANGE, Type.PRACTICE, "Projection par la tête – hachi mawashi", "", "", ""));
        localQuestionList.add(new Question(89, Level.ORANGE, Type.PRACTICE, "Projection par le bras enroulé – shiho nage", "", "", ""));
        localQuestionList.add(new Question(90, Level.ORANGE, Type.PRACTICE, "Projection par l’épaule à l’extérieur –(tembim nage ou ude kakae)", "", "", ""));
        localQuestionList.add(new Question(91, Level.ORANGE, Type.PRACTICE, "chute lattérale plaquée à gauche", "", "", ""));
        localQuestionList.add(new Question(92, Level.GREEN, Type.PRACTICE, "Ushiro kata otoshi", "", "", ""));
        localQuestionList.add(new Question(93, Level.GREEN, Type.PRACTICE, "Clé de bras en flexion sur l’épaule (début de shiho nage)", "", "", ""));
        localQuestionList.add(new Question(94, Level.GREEN, Type.PRACTICE, "Clé de bras en flexion dans le dos (ude gatame)", "", "", ""));
        localQuestionList.add(new Question(95, Level.GREEN, Type.PRACTICE, "Clé sur le bras tendu (ude gatame)", "", "", ""));
        localQuestionList.add(new Question(96, Level.GREEN, Type.PRACTICE, "Clé de bras en enroulement (3 formes) – ude garami", "", "", ""));
        localQuestionList.add(new Question(97, Level.GREEN, Type.PRACTICE, "Clé sur le ventre – Hara gatame", "", "", ""));
        localQuestionList.add(new Question(98, Level.GREEN, Type.PRACTICE, "Balayage de pied – de hachi barai", "", "", ""));
        localQuestionList.add(new Question(99, Level.GREEN, Type.PRACTICE, "Sutemi avant – tomoe nage", "", "", ""));
        localQuestionList.add(new Question(100, Level.GREEN, Type.PRACTICE, "Sutemi arrière (tani otoshi)", "", "", ""));
        localQuestionList.add(new Question(101, Level.GREEN, Type.PRACTICE, "Sutemi arrière en ciseau de jambe (kami basami)", "", "", ""));
        localQuestionList.add(new Question(102, Level.GREEN, Type.PRACTICE, "Projection en renversement arrière (do gaeshi)", "", "", ""));
        localQuestionList.add(new Question(103, Level.GREEN, Type.PRACTICE, " Sutemi latéral – yoko sutemi", "", "", ""));
        localQuestionList.add(new Question(104, Level.GREEN, Type.PRACTICE, "Chargement sur les deux épaules (debout) – kata Guruma", "", "", ""));
        localQuestionList.add(new Question(105, Level.GREEN, Type.PRACTICE, "Chargement sur les deux épaules (un genou au sol) – Tamara Guruma", "", "", ""));
        localQuestionList.add(new Question(106, Level.GREEN, Type.PRACTICE, "Immobilisation par clé de poignet bras tendu, uke sur le dos (tâte gassho gatame)", "", "", ""));
        localQuestionList.add(new Question(107, Level.GREEN, Type.PRACTICE, "Immobilisation par clé en torsion de main, uke sur le dos (kote gaeshi gatame)", "", "", ""));
        localQuestionList.add(new Question(108, Level.GREEN, Type.PRACTICE, "Immobilisation par clé en flexion de bras, uke sur le dos (ude garami)", "", "", ""));
        localQuestionList.add(new Question(109, Level.GREEN, Type.PRACTICE, "Immobilisation par clé sur le poignet, uke sur le ventre (kote gatame)", "", "", ""));
        localQuestionList.add(new Question(110, Level.GREEN, Type.PRACTICE, "Immobilisation par clé en triangle, uke sur le ventre (sankaku gatame)", "", "", ""));
        localQuestionList.add(new Question(111, Level.GREEN, Type.PRACTICE, "Immobilisation par clé en pression au coude (uke sur le ventre)", "", "", ""));
        localQuestionList.add(new Question(112, Level.GREEN, Type.PRACTICE, "Renversement du corps en barrage (harai goshi)", "", "", ""));
        localQuestionList.add(new Question(113, Level.GREEN, Type.PRACTICE, "Atemi à quatre phalanges - hiraken", "", "", ""));
        localQuestionList.add(new Question(114, Level.GREEN, Type.PRACTICE, "Kakato geri", "", "", ""));
        localQuestionList.add(new Question(115, Level.BROWN, Type.PRACTICE, "Etranglement à main nu (hadaka jime)", "", "", ""));
        localQuestionList.add(new Question(116, Level.GREEN, Type.PRACTICE, "Projection par rammassement de jambe (sukui nage)", "", "", ""));
        localQuestionList.add(new Question(117, Level.GREEN, Type.PRACTICE, "Projection de la 'rame' (robuse ou ude osae)", "", "", ""));
        localQuestionList.add(new Question(118, Level.GREEN, Type.PRACTICE, "Coup de pied latéral - Yoko Geri", "", "", ""));
        localQuestionList.add(new Question(119, Level.BROWN, Type.THEORY, "Qu'est ce qu'un KIHON IPPON KUMITE ?", "Assaut conventionnel: une attaque contrée par une seule défense et ou contre-attaque", "", ""));
        localQuestionList.add(new Question(120, Level.ORANGE, Type.THEORY, "Que signifie RITSUREI ?", "Salut debout", "", ""));
        localQuestionList.add(new Question(121, Level.ORANGE, Type.THEORY, "Que signifie ZAREI ?", "Salut à genoux", "", ""));
        localQuestionList.add(new Question(122, Level.GREEN, Type.THEORY, "Que signifie REISHIKI ?", "Cérémonial", "", ""));
        localQuestionList.add(new Question(123, Level.YELLOW, Type.THEORY, "Que signifie SEIZA ?", "Position à genoux", "", ""));
        localQuestionList.add(new Question(124, Level.YELLOW, Type.THEORY, "Qu'exprime le mot YAME ?", "Fin de l’exercice retour en YOI (toujours vigilent)", "", ""));
        localQuestionList.add(new Question(125, Level.YELLOW, Type.THEORY, "Qu'exprime le mot YASSME ?", "Se décontracter (intervient YAME)", "", ""));
        localQuestionList.add(new Question(126, Level.YELLOW, Type.THEORY, "Qu'exprime le mot YOI ?", "Position et état mental concentré et vigilant", "", ""));
        localQuestionList.add(new Question(127, Level.YELLOW, Type.THEORY, "Que signifie KIMONO ?", "littéralement  'chose que l'on porte sur soi'.", "", ""));
        localQuestionList.add(new Question(128, Level.YELLOW, Type.THEORY, "Que signifie KEIKO GI ?", "Vêtement d’entraînement'", "", ""));
        localQuestionList.add(new Question(129, Level.GREEN, Type.PRACTICE, "Yama arashi", "", "", ""));
        localQuestionList.add(new Question(130, Level.BLUE, Type.PRACTICE, "Technique de 'viens donc' par clé en bec de cygne", "", "", ""));
        localQuestionList.add(new Question(131, Level.BLUE, Type.PRACTICE, "Technique de 'viens donc' par le pouce", "", "", ""));
        localQuestionList.add(new Question(132, Level.BLUE, Type.PRACTICE, "Technique de 'viens donc' par clé de bras en croix", "", "", ""));
        localQuestionList.add(new Question(133, Level.BLUE, Type.PRACTICE, "étranglement nu (hadaka jime)", "", "", ""));
        localQuestionList.add(new Question(134, Level.BLUE, Type.PRACTICE, "étranglement en enroulant par la manche (sode guruma jime)", "", "", ""));
        localQuestionList.add(new Question(135, Level.BLUE, Type.PRACTICE, "étranglement en contrôlant l'épaule (kata ha jime)", "", "", ""));
        localQuestionList.add(new Question(136, Level.BLUE, Type.PRACTICE, "étranglement avec le revers (eri jime)", "", "", ""));
        localQuestionList.add(new Question(137, Level.BLUE, Type.PRACTICE, "Coup de pied direct sauté (Mae Tobi Geri)", "", "", ""));
        localQuestionList.add(new Question(138, Level.BLUE, Type.PRACTICE, "Coup de pied en ruade (Ushiro Geri)", "", "", ""));
        localQuestionList.add(new Question(139, Level.BLUE, Type.PRACTICE, "Coup de pied circulaire retourné - Ushiro Ura Mawashi Geri", "", "", ""));
        localQuestionList.add(new Question(140, Level.BLUE, Type.PRACTICE, "Immobilisation par clé de cheville en croix, uke sur le dos (kata ashi hishigi)", "", "", ""));
        localQuestionList.add(new Question(141, Level.BLUE, Type.PRACTICE, "Immobilisation par clé de jambe fléchie, uke sur le ventre (hiza hishigi)", "", "", ""));
        localQuestionList.add(new Question(142, Level.BLUE, Type.PRACTICE, "Immobilisation par clé de cheville en torsion vers l'intérieur, uke sur le dos (ashi dori garami)", "", "", ""));
        localQuestionList.add(new Question(143, Level.ORANGE, Type.PRACTICE, "Garde au sol", "", "", ""));
        localQuestionList.add(new Question(144, Level.BLUE, Type.PRACTICE, "Sol: Tori au sol, uke debout devant (plusieurs formes d’atemi et de ciseaux)", "", "", ""));
        localQuestionList.add(new Question(145, Level.BLUE, Type.PRACTICE, "Sol: Uke à cheval sur Tori", "", "", ""));
        localQuestionList.add(new Question(146, Level.BLUE, Type.PRACTICE, "Sol: Uke attaque entre les jambes de Tori", "", "", ""));
        localQuestionList.add(new Question(147, Level.BLUE, Type.PRACTICE, "Sol: Uke est derrière Tori", "", "", ""));
        localQuestionList.add(new Question(148, Level.BLUE, Type.PRACTICE, "Sol: Tori renverse Uke", "", "", ""));
        localQuestionList.add(new Question(149, Level.BLUE, Type.PRACTICE, "Crochet (Mawashi Tsuki)", "", "", ""));
        localQuestionList.add(new Question(150, Level.BLUE, Type.PRACTICE, "Upercut", "", "", ""));
        localQuestionList.add(new Question(151, Level.BROWN, Type.PRACTICE, "projection extérieure (soto tenkai)", "", "", ""));
        localQuestionList.add(new Question(152, Level.BROWN, Type.PRACTICE, "projection par le col et par l'arrière (ushiro eri kata nage)", "", "", ""));
        localQuestionList.add(new Question(153, Level.BROWN, Type.PRACTICE, "projection lattérale par poussée du coude (ude oshiage)", "", "", ""));
        localQuestionList.add(new Question(154, Level.BROWN, Type.PRACTICE, "projection arrière en poussée du menton (ago ate nage)", "", "", ""));
        localQuestionList.add(new Question(155, Level.BLACK, Type.PRACTICE, "projection par traction de la manche (uki otoshi)", "", "", ""));
        localQuestionList.add(new Question(156, Level.BLACK, Type.PRACTICE, "projection par traction de la manche et du cou (kubi uki otoshi)", "", "", ""));
        localQuestionList.add(new Question(157, Level.BLACK, Type.PRACTICE, "sutemi par le cou (kubi nage sutemi)", "", "", ""));
        localQuestionList.add(new Question(158, Level.BLACK, Type.PRACTICE, "projection par par levier de l'avant bras au genou (ashi otoshi)", "", "", ""));
        localQuestionList.add(new Question(159, Level.BLACK, Type.PRACTICE, "projection par fauchage bras tendu (ude maki soto gari)", "", "", ""));
        localQuestionList.add(new Question(160, Level.BLACK, Type.PRACTICE, "sutemi (tobi nori sutemi)", "", "", ""));
        localQuestionList.add(new Question(161, Level.BLACK, Type.PRACTICE, "projection enroulée par la manche (sode guruma otoshi)", "", "", ""));
        localQuestionList.add(new Question(162, Level.BLACK, Type.PRACTICE, "Yoko sutemi (yoko otoshi)", "", "", ""));
        localQuestionList.add(new Question(163, Level.BLACK, Type.PRACTICE, "sutemi dans l'angle (sumi gaeshi)", "", "", ""));
        localQuestionList.add(new Question(164, Level.BLACK, Type.PRACTICE, "kubi mawashi sutemi", "", "", ""));
        localQuestionList.add(new Question(165, Level.BLACK, Type.PRACTICE, "yoko guruma", "", "", ""));
        localQuestionList.add(new Question(166, Level.BLACK, Type.PRACTICE, "ude gake sutemi", "", "", ""));
        localQuestionList.add(new Question(167, Level.BROWN, Type.THEORY, "Que signifie Kamiza?", "Place d'honneur", "", ""));
        localQuestionList.add(new Question(168, Level.BROWN, Type.THEORY, "Qu'est-ce qu'un Kuatsu?", "Une technique de soin ou de réanimation par percussion de zone particulères du corps", "", ""));
        localQuestionList.add(new Question(169, Level.BLACK, Type.PRACTICE, "eri tori sutemi", "", "", ""));
        localQuestionList.add(new Question(170, Level.BLACK, Type.PRACTICE, "Yoko tomoe", "", "", ""));
        localQuestionList.add(new Question(171, Level.BLACK, Type.PRACTICE, "atama guruma", "", "", ""));
        localQuestionList.add(new Question(172, Level.YELLOW, Type.THEORY, "Que signifie KAMAE ?", "En garde !", "", ""));
        localQuestionList.add(new Question(173, Level.ORANGE, Type.THEORY, "Quels sont les ordres japonais donnés par le sampai en fin de cours?", "Seiza / Shomen ni rei / Senseï ni rei / Otagaï ni rei / Kiritsu", "", ""));
        localQuestionList.add(new Question(174, Level.YELLOW, Type.THEORY, "Que signifie Yoi ?", "Position de vigilance physique et mentale et un ordre donné par le professeur de prendre la position Yoi", "", ""));
        localQuestionList.add(new Question(175, Level.YELLOW, Type.THEORY, "Que signifie SEIZA ?", "Position agenouillée et ordre donné par le professeur de se mettre à genou", "", ""));
        localQuestionList.add(new Question(176, Level.ORANGE, Type.THEORY, "Que signifie 'Senseï ni rei'", "Saluez le professeur (ordre donné par le sampai lors du cérémonial de début et fin de cours)", "", ""));
        localQuestionList.add(new Question(177, Level.ORANGE, Type.THEORY, "Que signifie 'Otagaï ni rei'", "Saluez-vous entre élèves (ordre donné par le sampai lors du cérémonial de début et fin de cours)", "", ""));
        localQuestionList.add(new Question(178, Level.ORANGE, Type.THEORY, "Que signifie 'Shomen ni rei'", "Saluez la mémoire des ancêtres (ordre donné par le sampai lors du cérémonial de début et fin de cours)", "", ""));
        localQuestionList.add(new Question(179, Level.YELLOW, Type.THEORY, "Que signifie Kiritsu", "Relevez-vous (ordre donné par le sampai lors du cérémonial de début et fin de cours)", "", ""));
        localQuestionList.add(new Question(180, Level.YELLOW, Type.THEORY, "Que signifie Hajime", "Commencez (l'exercice, le combat…)", "", ""));
        localQuestionList.add(new Question(181, Level.YELLOW, Type.THEORY, "Que signifie Matte", "Arrêtez-vous (ordre très important)", "", ""));
        localQuestionList.add(new Question(182, Level.GREEN, Type.THEORY, "Que signifie ONEGAISHIMASU", "« s’il vous plait » formule de politesse « Voulez-vous travailler avec moi ? SVP »", "", ""));
        localQuestionList.add(new Question(183, Level.GREEN, Type.THEORY, "Que signifie ARIGATO GOZAIMASU", "merci beaucoup (pour vos conseils, pour avoir travaillé avec moi, pour le cours, etc…)", "", ""));
        localQuestionList.add(new Question(184, Level.YELLOW, Type.THEORY, "Que signifie ICHI, NI, SAN, SHI (YON), GO, ROKU, SHICHI (NANA), ACHI, KYU, JUU ?", "Compte de 1 à 10 ", "", ""));
        localQuestionList.add(new Question(185, Level.YELLOW, Type.THEORY, "Que signifie REI ?", "Saluez", "", ""));
        localQuestionList.add(new Question(186, Level.ORANGE, Type.PRACTICE, "Parade à deux niveaux", "", "", ""));
        localQuestionList.add(new Question(187, Level.BLUE, Type.PRACTICE, "Technique de 'viens donc' par clé et étranglement", "", "", ""));
        localQuestionList.add(new Question(188, Level.BLUE, Type.PRACTICE, "Coup de pied sauté circulaire (Mawashi Tobi Geri)", "", "", ""));
        localQuestionList.add(new Question(189, Level.ORANGE, Type.PRACTICE, "Ryote Ippon Seoi Nage", "", "", ""));
        localQuestionList.add(new Question(190, Level.BLACK, Type.PRACTICE, "Yoko wakare", "", "", ""));
        localQuestionList.add(new Question(191, Level.BLACK, Type.PRACTICE, "Sutemi arrière par les hanches", "", "", ""));
        localQuestionList.add(new Question(192, Level.GREEN, Type.PRACTICE, "Immobilisation par clé de bras en croix (ude juji gatame)", "", "", ""));
        localQuestionList.add(new Question(193, Level.GREEN, Type.PRACTICE, "Juji gatame", "", "", ""));
        localQuestionList.add(new Question(194, Level.GREEN, Type.PRACTICE, "immobilisation par Ude Garami", "", "", ""));
        localQuestionList.add(new Question(195, Level.GREEN, Type.PRACTICE, "Différentes formes de clé de pouce", "", "", ""));
        localQuestionList.add(new Question(196, Level.ORANGE, Type.PRACTICE, "Nekko ahshi dachi", "", "https://drive.google.com/file/d/172c9A0GCUnp36MKm8pD4SCQri1ixwtxQ/view?usp=sharing", ""));
        localQuestionList.add(new Question(197, Level.ORANGE, Type.PRACTICE, "Marteau de fer - tetsui", "", "", ""));
        localQuestionList.add(new Question(198, Level.BLUE, Type.PRACTICE, "Coup de pied circulaire inversé - Ura Mawashi Geri", "", "", ""));
        localQuestionList.add(new Question(199, Level.ORANGE, Type.PRACTICE, "Frappe en revers de poing (uraken uchi)", "", "", ""));
        localQuestionList.add(new Question(200, Level.YELLOW, Type.PRACTICE, "Kihon waza - déplacements seuls", "", "", ""));
        localQuestionList.add(new Question(201, Level.YELLOW, Type.PRACTICE, "Kihon waza - déplacements et parades", "", "", ""));
        localQuestionList.add(new Question(202, Level.ORANGE, Type.PRACTICE, "Kihon waza - déplacements et parades à deux", "", "", ""));
        return localQuestionList;
    }

    public String getInitDescription(){
        return initDescription;
    }
    public interface Initiator {
        void loaded(Database database);
    }

}
