package org.ntj_workout.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

    private List<Question> questionList;

    public Database init() {
        this.questionList = new LinkedList<>();

        //"https://drive.google.com/uc?export=view&id="
        this.questionList.add(new Question(1,Level.YELLOW,Type.PRACTICE,"Base 1 par atemi","","",""));
        this.questionList.add(new Question(2,Level.YELLOW,Type.PRACTICE,"Base 2 par atemi","","",""));
        this.questionList.add(new Question(3,Level.YELLOW,Type.PRACTICE,"Base 3 par atemi  (deux formes)","","",""));
        this.questionList.add(new Question(4,Level.YELLOW,Type.PRACTICE,"Base 4 par atemi (deux formes)","","",""));
        this.questionList.add(new Question(5,Level.YELLOW,Type.PRACTICE,"Base 5 par atemi","","",""));
        this.questionList.add(new Question(6,Level.YELLOW,Type.PRACTICE,"Base 6 par atemi","","",""));
        this.questionList.add(new Question(7,Level.YELLOW,Type.PRACTICE,"Base 7 par atemi","","",""));
        this.questionList.add(new Question(8,Level.YELLOW,Type.PRACTICE,"Base 8 par atemi ( (deux formes)","","",""));
        this.questionList.add(new Question(9,Level.YELLOW,Type.PRACTICE,"Faire son noeud de ceinture","","","https://youtube.com/shorts/1tJwxh5NRWw"));
        this.questionList.add(new Question(10,Level.YELLOW,Type.PRACTICE,"Salut debout","","","https://youtube.com/shorts/a4tf8D4PS3g"));
        this.questionList.add(new Question(11,Level.YELLOW,Type.PRACTICE,"Position ZENKUTSU DACHI","","","https://youtube.com/shorts/z9WK-N7Fk94"));
        this.questionList.add(new Question(12,Level.YELLOW,Type.PRACTICE,"Position KOKUTSU DACHI","","",""));
        this.questionList.add(new Question(13,Level.YELLOW,Type.PRACTICE,"Position SHIKO DACHI","","","https://youtu.be/fLacl3wm-lw"));
        this.questionList.add(new Question(14,Level.YELLOW,Type.PRACTICE,"Salut à genou","","","https://youtube.com/shorts/1mf8sh3Oh-w"));
        this.questionList.add(new Question(15,Level.YELLOW,Type.PRACTICE,"Clé de bras sous l'aisselle - waki gatame","","","https://youtube.com/shorts/XfH_qxQHZwY"));
        this.questionList.add(new Question(16,Level.YELLOW,Type.PRACTICE,"Clé de bras tendue sur l'épaule - Tembim Gatame","","","https://youtube.com/shorts/Hnm6xD6r_6w"));
        this.questionList.add(new Question(17,Level.YELLOW,Type.PRACTICE,"Mise en garde classique","","","https://youtube.com/shorts/DvgO0KjN9DM"));
        this.questionList.add(new Question(18,Level.YELLOW,Type.PRACTICE,"Mise en garde garde mixte","","","https://youtube.com/shorts/X7DfwTBnui8"));
        this.questionList.add(new Question(19,Level.YELLOW,Type.PRACTICE,"Mise en garde garde « kempo »","","","https://youtube.com/shorts/K8ql_UtPKls"));
        this.questionList.add(new Question(20,Level.YELLOW,Type.PRACTICE,"Esquive en avançant (IRIMI)","","",""));
        this.questionList.add(new Question(21,Level.YELLOW,Type.PRACTICE,"Esquive en reculant (NAGASHI)","","",""));
        this.questionList.add(new Question(22,Level.YELLOW,Type.PRACTICE,"Esquive latérale (HIRAKI)","","",""));
        this.questionList.add(new Question(23,Level.YELLOW,Type.PRACTICE,"Esquive rotative (IRIMI SENKAI)","","",""));
        this.questionList.add(new Question(24,Level.YELLOW,Type.PRACTICE,"Parade basse","","",""));
        this.questionList.add(new Question(25,Level.YELLOW,Type.PRACTICE,"Parade niveau moyen","","",""));
        this.questionList.add(new Question(26,Level.YELLOW,Type.PRACTICE,"Parade haute","","",""));
        this.questionList.add(new Question(27,Level.YELLOW,Type.PRACTICE,"Clé de bras en croix (juji ude gatame)","","",""));
        this.questionList.add(new Question(28,Level.YELLOW,Type.PRACTICE,"Clé de poignet vers l'extérieur (kote gaeshi)","","",""));
        this.questionList.add(new Question(29,Level.YELLOW,Type.PRACTICE,"projection 'en rentrant' (mukae daoshi ou irimi nage)","","",""));
        this.questionList.add(new Question(30,Level.YELLOW,Type.PRACTICE,"Grand fauchage de la jambe extérieure (O soto gari)","","",""));
        this.questionList.add(new Question(31,Level.YELLOW,Type.PRACTICE,"Changement de garde en avançant et en reculant","","",""));
        this.questionList.add(new Question(32,Level.YELLOW,Type.PRACTICE,"Mise en YOI","","https://ecolekaratemarais.fr/wp-content/uploads/2020/05/Yoi.jpg",""));
        this.questionList.add(new Question(33,Level.YELLOW,Type.PRACTICE,"Demi tour en garde","","",""));
        this.questionList.add(new Question(34,Level.YELLOW,Type.PRACTICE,"Chute avant roulée à droite","","",""));
        this.questionList.add(new Question(35,Level.ORANGE,Type.PRACTICE,"Chute avant plaquée à droite","","",""));
        this.questionList.add(new Question(36,Level.YELLOW,Type.PRACTICE,"Chute arrière roulée à droite","","",""));
        this.questionList.add(new Question(37,Level.YELLOW,Type.PRACTICE,"Chute arrière plaquée","","",""));
        this.questionList.add(new Question(38,Level.YELLOW,Type.THEORY,"Ordres japonais donnés par le sampai en début de cours?","Seiza / Otagaï ni rei / Senseï ni rei / shomen ni rei / Kiritsu","",""));
        this.questionList.add(new Question(39,Level.YELLOW,Type.THEORY,"Que signifie DACHI ?","Position","",""));
        this.questionList.add(new Question(40,Level.YELLOW,Type.PRACTICE,"Coup de pied direct (mae geri)","","",""));
        this.questionList.add(new Question(41,Level.YELLOW,Type.PRACTICE,"Coup de poing direct en avançant (oi tsuki)","","",""));
        this.questionList.add(new Question(42,Level.YELLOW,Type.PRACTICE,"Coup de poing sen opposition (gyaku tsuki)","","",""));
        this.questionList.add(new Question(43,Level.YELLOW,Type.PRACTICE,"Coup de coude (empi uchi)","","",""));
        this.questionList.add(new Question(44,Level.YELLOW,Type.PRACTICE,"Fouetté des doigts (me uchi)","","",""));
        this.questionList.add(new Question(45,Level.YELLOW,Type.PRACTICE,"Coup de genou (hiza geri)","","",""));
        this.questionList.add(new Question(46,Level.YELLOW,Type.PRACTICE,"Chute latérale à droite","","",""));
        this.questionList.add(new Question(47,Level.YELLOW,Type.PRACTICE,"Garde au sol","","",""));
        this.questionList.add(new Question(48,Level.ORANGE,Type.PRACTICE,"base 1 par clé","","",""));
        this.questionList.add(new Question(49,Level.YELLOW,Type.THEORY,"Que signifie TAI-SABAKI ?","Esquive","",""));
        this.questionList.add(new Question(50,Level.ORANGE,Type.PRACTICE,"base 1 par projection","","",""));
        this.questionList.add(new Question(51,Level.ORANGE,Type.PRACTICE,"base 2 par clé","","",""));
        this.questionList.add(new Question(52,Level.ORANGE,Type.PRACTICE,"base 2 par projection","","",""));
        this.questionList.add(new Question(53,Level.ORANGE,Type.PRACTICE,"base 3 par clé","","",""));
        this.questionList.add(new Question(54,Level.YELLOW,Type.THEORY,"Que signifie TORI ?","Le défenseur, celui qui exécute la technique","",""));
        this.questionList.add(new Question(55,Level.YELLOW,Type.THEORY,"Que signifie UKE ?","L’attaquant, celui qui la subit la technique","",""));
        this.questionList.add(new Question(56,Level.ORANGE,Type.THEORY,"Que signifie WAZA ?","Techniques","",""));
        this.questionList.add(new Question(57,Level.ORANGE,Type.PRACTICE,"base 3 par projection","","",""));
        this.questionList.add(new Question(58,Level.ORANGE,Type.PRACTICE,"base 4 par clé","","",""));
        this.questionList.add(new Question(59,Level.ORANGE,Type.PRACTICE,"base 4 par projection","","",""));
        this.questionList.add(new Question(60,Level.ORANGE,Type.PRACTICE,"base 5 par clé","","",""));
        this.questionList.add(new Question(61,Level.ORANGE,Type.PRACTICE,"base 5 par projection","","",""));
        this.questionList.add(new Question(62,Level.ORANGE,Type.PRACTICE,"base 6 par clé","","",""));
        this.questionList.add(new Question(63,Level.ORANGE,Type.PRACTICE,"base 6 par projection","","",""));
        this.questionList.add(new Question(64,Level.YELLOW,Type.THEORY,"Que signifie ATEMI ?","Coup frappé par une partie sensible du corps","",""));
        this.questionList.add(new Question(65,Level.ORANGE,Type.PRACTICE,"base 7 par clé","","",""));
        this.questionList.add(new Question(66,Level.ORANGE,Type.PRACTICE,"base 7 par projection","","",""));
        this.questionList.add(new Question(67,Level.ORANGE,Type.PRACTICE,"base 8 par clé","","",""));
        this.questionList.add(new Question(68,Level.ORANGE,Type.PRACTICE,"base 8 par projection","","",""));
        this.questionList.add(new Question(69,Level.ORANGE,Type.THEORY,"Que signifie UKEMI ?","Chute","",""));
        this.questionList.add(new Question(70,Level.ORANGE,Type.PRACTICE,"Frappe en sabre haut diagonal (yokomen uchi)","","",""));
        this.questionList.add(new Question(71,Level.ORANGE,Type.PRACTICE,"Frappe en sabre de main haut (shomen  uchi)","","",""));
        this.questionList.add(new Question(72,Level.ORANGE,Type.PRACTICE,"Différentes formes de coup de coude (empi uchi)","Debout pieds écartés et parallèles","",""));
        this.questionList.add(new Question(73,Level.ORANGE,Type.PRACTICE,"Coup de pied circulaire (mawashi geri)","Position du cavalier","",""));
        this.questionList.add(new Question(74,Level.ORANGE,Type.PRACTICE,"Chute avant roulée à gauche","","",""));
        this.questionList.add(new Question(75,Level.ORANGE,Type.PRACTICE,"Chute avant plaquée à gauche","","",""));
        this.questionList.add(new Question(76,Level.ORANGE,Type.PRACTICE,"Chute arrière roulée à gauche","","",""));
        this.questionList.add(new Question(77,Level.ORANGE,Type.PRACTICE,"Clé de main inversée (kote gatame)","","",""));
        this.questionList.add(new Question(78,Level.ORANGE,Type.PRACTICE,"Clé en enroulement (ude garami)","","",""));
        this.questionList.add(new Question(79,Level.ORANGE,Type.PRACTICE,"Clé de poignet en torsion vers l'extérieur par le pouce (gyaku kote gaeshi)","","",""));
        this.questionList.add(new Question(80,Level.ORANGE,Type.PRACTICE,"Clé de poignet en torsion vers l'intérieur (yuki chigae = kote hineri)","","",""));
        this.questionList.add(new Question(81,Level.ORANGE,Type.PRACTICE,"Clé sur le pouce","","",""));
        this.questionList.add(new Question(82,Level.ORANGE,Type.PRACTICE,"Clé de 'bras en Z' (kote kudaki)","","",""));
        this.questionList.add(new Question(83,Level.ORANGE,Type.PRACTICE,"Crochetage de jambe intérieure – Ko uchi gari","","",""));
        this.questionList.add(new Question(84,Level.ORANGE,Type.PRACTICE,"Chargement de hanche – O goshi","","",""));
        this.questionList.add(new Question(85,Level.ORANGE,Type.PRACTICE,"Chargement au cou – kubi nage","","",""));
        this.questionList.add(new Question(86,Level.ORANGE,Type.PRACTICE,"Chargement d’épaule à l’intérieur – ippon seoi nage","","",""));
        this.questionList.add(new Question(87,Level.ORANGE,Type.PRACTICE,"Ramassement de jambes – do gaeshi","","",""));
        this.questionList.add(new Question(88,Level.ORANGE,Type.PRACTICE,"Projection par la tête – hachi mawashi","","",""));
        this.questionList.add(new Question(89,Level.ORANGE,Type.PRACTICE,"Projection par le bras enroulé – shiho nage","","",""));
        this.questionList.add(new Question(90,Level.ORANGE,Type.PRACTICE,"Projection par l’épaule à l’extérieur –(tembim nage ou ude kakae)","","",""));
        this.questionList.add(new Question(91,Level.ORANGE,Type.PRACTICE,"chute lattérale plaquée à gauche","","",""));
        this.questionList.add(new Question(92,Level.GREEN,Type.PRACTICE,"Ushiro kata otoshi","","",""));
        this.questionList.add(new Question(93,Level.GREEN,Type.PRACTICE,"Clé de bras en flexion sur l’épaule (début de shiho nage)","","",""));
        this.questionList.add(new Question(94,Level.GREEN,Type.PRACTICE,"Clé de bras en flexion dans le dos (ude gatame)","","",""));
        this.questionList.add(new Question(95,Level.GREEN,Type.PRACTICE,"Clé sur le bras tendu (ude gatame)","","",""));
        this.questionList.add(new Question(96,Level.GREEN,Type.PRACTICE,"Clé de bras en enroulement (3 formes) – ude garami","","",""));
        this.questionList.add(new Question(97,Level.GREEN,Type.PRACTICE,"Clé sur le ventre – Hara gatame","","",""));
        this.questionList.add(new Question(98,Level.GREEN,Type.PRACTICE,"Balayage de pied – de hachi barai","","",""));
        this.questionList.add(new Question(99,Level.GREEN,Type.PRACTICE,"Sutemi avant – tomoe nage","","",""));
        this.questionList.add(new Question(100,Level.GREEN,Type.PRACTICE,"Sutemi arrière (tani otoshi)","","",""));
        this.questionList.add(new Question(101,Level.GREEN,Type.PRACTICE,"Sutemi arrière en ciseau de jambe (kami basami)","","",""));
        this.questionList.add(new Question(102,Level.GREEN,Type.PRACTICE,"Projection en renversement arrière (do gaeshi)","","",""));
        this.questionList.add(new Question(103,Level.GREEN,Type.PRACTICE," Sutemi latéral – yoko sutemi","","",""));
        this.questionList.add(new Question(104,Level.GREEN,Type.PRACTICE,"Chargement sur les deux épaules (debout) – kata Guruma","","",""));
        this.questionList.add(new Question(105,Level.GREEN,Type.PRACTICE,"Chargement sur les deux épaules (un genou au sol) – Tamara Guruma","","",""));
        this.questionList.add(new Question(106,Level.GREEN,Type.PRACTICE,"Immobilisation par clé de poignet bras tendu, uke sur le dos (tâte gassho gatame)","","",""));
        this.questionList.add(new Question(107,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en torsion de main, uke sur le dos (kote gaeshi gatame)","","",""));
        this.questionList.add(new Question(108,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en flexion de bras, uke sur le dos (ude garami)","","",""));
        this.questionList.add(new Question(109,Level.GREEN,Type.PRACTICE,"Immobilisation par clé sur le poignet, uke sur le ventre (kote gatame)","","",""));
        this.questionList.add(new Question(110,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en triangle, uke sur le ventre (sankaku gatame)","","",""));
        this.questionList.add(new Question(111,Level.GREEN,Type.PRACTICE,"Immobilisation par clé en pression au coude (uke sur le ventre)","","",""));
        this.questionList.add(new Question(112,Level.GREEN,Type.PRACTICE,"Renversement du corps en barrage (harai goshi)","","",""));
        this.questionList.add(new Question(113,Level.GREEN,Type.PRACTICE,"Atemi à quatre phalanges - hiraken","","",""));
        this.questionList.add(new Question(114,Level.GREEN,Type.PRACTICE,"Kakato geri","","",""));
        this.questionList.add(new Question(115,Level.BROWN,Type.PRACTICE,"Etranglement à main nu (hadaka jime)","","",""));
        this.questionList.add(new Question(116,Level.GREEN,Type.PRACTICE,"Projection par rammassement de jambe (sukui nage)","","",""));
        this.questionList.add(new Question(117,Level.GREEN,Type.PRACTICE,"Projection de la 'rame' (robuse ou ude osae)","","",""));
        this.questionList.add(new Question(118,Level.GREEN,Type.PRACTICE,"Coup de pied latéral - Yoko Geri","","",""));
        this.questionList.add(new Question(119,Level.BROWN,Type.THEORY,"Qu'est ce qu'un KIHON IPPON KUMITE ?","Assaut conventionnel: une attaque contrée par une seule défense et ou contre-attaque","",""));
        this.questionList.add(new Question(120,Level.ORANGE,Type.THEORY,"Que signifie RITSUREI ?","Salut debout","",""));
        this.questionList.add(new Question(121,Level.ORANGE,Type.THEORY,"Que signifie ZAREI ?","Salut à genoux","",""));
        this.questionList.add(new Question(122,Level.GREEN,Type.THEORY,"Que signifie REISHIKI ?","Cérémonial","",""));
        this.questionList.add(new Question(123,Level.YELLOW,Type.THEORY,"Que signifie SEIZA ?","Position à genoux","",""));
        this.questionList.add(new Question(124,Level.YELLOW,Type.THEORY,"Qu'exprime le mot YAME ?","Fin de l’exercice retour en YOI (toujours vigilent)","",""));
        this.questionList.add(new Question(125,Level.YELLOW,Type.THEORY,"Qu'exprime le mot YASSME ?","Se décontracter (intervient YAME)","",""));
        this.questionList.add(new Question(126,Level.YELLOW,Type.THEORY,"Qu'exprime le mot YOI ?","Position et état mental concentré et vigilant","",""));
        this.questionList.add(new Question(127,Level.YELLOW,Type.THEORY,"Que signifie KIMONO ?","littéralement  'chose que l'on porte sur soi'.","",""));
        this.questionList.add(new Question(128,Level.YELLOW,Type.THEORY,"Que signifie KEIKO GI ?","Vêtement d’entraînement'","",""));
        this.questionList.add(new Question(129,Level.GREEN,Type.PRACTICE,"Yama arashi","","",""));
        this.questionList.add(new Question(130,Level.BLUE,Type.PRACTICE,"Technique de 'viens donc' par clé en bec de cygne","","",""));
        this.questionList.add(new Question(131,Level.BLUE,Type.PRACTICE,"Technique de 'viens donc' par le pouce","","",""));
        this.questionList.add(new Question(132,Level.BLUE,Type.PRACTICE,"Technique de 'viens donc' par clé de bras en croix","","",""));
        this.questionList.add(new Question(133,Level.BLUE,Type.PRACTICE,"étranglement nu (hadaka jime)","","",""));
        this.questionList.add(new Question(134,Level.BLUE,Type.PRACTICE,"étranglement en enroulant par la manche (sode guruma jime)","","",""));
        this.questionList.add(new Question(135,Level.BLUE,Type.PRACTICE,"étranglement en contrôlant l'épaule (kata ha jime)","","",""));
        this.questionList.add(new Question(136,Level.BLUE,Type.PRACTICE,"étranglement avec le revers (eri jime)","","",""));
        this.questionList.add(new Question(137,Level.BLUE,Type.PRACTICE,"Coup de pied direct sauté (Mae Tobi Geri)","","",""));
        this.questionList.add(new Question(138,Level.BLUE,Type.PRACTICE,"Coup de pied en ruade (Ushiro Geri)","","",""));
        this.questionList.add(new Question(139,Level.BLUE,Type.PRACTICE,"Coup de pied circulaire retourné - Ushiro Ura Mawashi Geri","","",""));
        this.questionList.add(new Question(140,Level.BLUE,Type.PRACTICE,"Immobilisation par clé de cheville en croix, uke sur le dos (kata ashi hishigi)","","",""));
        this.questionList.add(new Question(141,Level.BLUE,Type.PRACTICE,"Immobilisation par clé de jambe fléchie, uke sur le ventre (hiza hishigi)","","",""));
        this.questionList.add(new Question(142,Level.BLUE,Type.PRACTICE,"Immobilisation par clé de cheville en torsion vers l'intérieur, uke sur le dos (ashi dori garami)","","",""));
        this.questionList.add(new Question(143,Level.ORANGE,Type.PRACTICE,"Garde au sol","","",""));
        this.questionList.add(new Question(144,Level.BLUE,Type.PRACTICE,"Sol: Tori au sol, uke debout devant (plusieurs formes d’atemi et de ciseaux)","","",""));
        this.questionList.add(new Question(145,Level.BLUE,Type.PRACTICE,"Sol: Uke à cheval sur Tori","","",""));
        this.questionList.add(new Question(146,Level.BLUE,Type.PRACTICE,"Sol: Uke attaque entre les jambes de Tori","","",""));
        this.questionList.add(new Question(147,Level.BLUE,Type.PRACTICE,"Sol: Uke est derrière Tori","","",""));
        this.questionList.add(new Question(148,Level.BLUE,Type.PRACTICE,"Sol: Tori renverse Uke","","",""));
        this.questionList.add(new Question(149,Level.BLUE,Type.PRACTICE,"Crochet (Mawashi Tsuki)","","",""));
        this.questionList.add(new Question(150,Level.BLUE,Type.PRACTICE,"Upercut","","",""));
        this.questionList.add(new Question(151,Level.BROWN,Type.PRACTICE,"projection extérieure (soto tenkai)","","",""));
        this.questionList.add(new Question(152,Level.BROWN,Type.PRACTICE,"projection par le col et par l'arrière (ushiro eri kata nage)","","",""));
        this.questionList.add(new Question(153,Level.BROWN,Type.PRACTICE,"projection lattérale par poussée du coude (ude oshiage)","","",""));
        this.questionList.add(new Question(154,Level.BROWN,Type.PRACTICE,"projection arrière en poussée du menton (ago ate nage)","","",""));
        this.questionList.add(new Question(155,Level.BLACK,Type.PRACTICE,"projection par traction de la manche (uki otoshi)","","",""));
        this.questionList.add(new Question(156,Level.BLACK,Type.PRACTICE,"projection par traction de la manche et du cou (kubi uki otoshi)","","",""));
        this.questionList.add(new Question(157,Level.BLACK,Type.PRACTICE,"sutemi par le cou (kubi nage sutemi)","","",""));
        this.questionList.add(new Question(158,Level.BLACK,Type.PRACTICE,"projection par par levier de l'avant bras au genou (ashi otoshi)","","",""));
        this.questionList.add(new Question(159,Level.BLACK,Type.PRACTICE,"projection par fauchage bras tendu (ude maki soto gari)","","",""));
        this.questionList.add(new Question(160,Level.BLACK,Type.PRACTICE,"sutemi (tobi nori sutemi)","","",""));
        this.questionList.add(new Question(161,Level.BLACK,Type.PRACTICE,"projection enroulée par la manche (sode guruma otoshi)","","",""));
        this.questionList.add(new Question(162,Level.BLACK,Type.PRACTICE,"Yoko sutemi (yoko otoshi)","","",""));
        this.questionList.add(new Question(163,Level.BLACK,Type.PRACTICE,"sutemi dans l'angle (sumi gaeshi)","","",""));
        this.questionList.add(new Question(164,Level.BLACK,Type.PRACTICE,"kubi mawashi sutemi","","",""));
        this.questionList.add(new Question(165,Level.BLACK,Type.PRACTICE,"yoko guruma","","",""));
        this.questionList.add(new Question(166,Level.BLACK,Type.PRACTICE,"ude gake sutemi","","",""));
        this.questionList.add(new Question(167,Level.BROWN,Type.THEORY,"Que signifie Kamiza?","Place d'honneur","",""));
        this.questionList.add(new Question(168,Level.BROWN,Type.THEORY,"Qu'est-ce qu'un Kuatsu?","Une technique de soin ou de réanimation par percussion de zone particulères du corps","",""));
        this.questionList.add(new Question(169,Level.BLACK,Type.PRACTICE,"eri tori sutemi","","",""));
        this.questionList.add(new Question(170,Level.BLACK,Type.PRACTICE,"Yoko tomoe","","",""));
        this.questionList.add(new Question(171,Level.BLACK,Type.PRACTICE,"atama guruma","","",""));
        this.questionList.add(new Question(172,Level.YELLOW,Type.THEORY,"Que signifie KAMAE ?","En garde !","",""));
        this.questionList.add(new Question(173,Level.ORANGE,Type.THEORY,"Quels sont les ordres japonais donnés par le sampai en fin de cours?","Seiza / Shomen ni rei / Senseï ni rei / Otagaï ni rei / Kiritsu","",""));
        this.questionList.add(new Question(174,Level.YELLOW,Type.THEORY,"Que signifie Yoi ?","Position de vigilance physique et mentale et un ordre donné par le professeur de prendre la position Yoi","",""));
        this.questionList.add(new Question(175,Level.YELLOW,Type.THEORY,"Que signifie SEIZA ?","Position agenouillée et ordre donné par le professeur de se mettre à genou","",""));
        this.questionList.add(new Question(176,Level.ORANGE,Type.THEORY,"Que signifie 'Senseï ni rei'","Saluez le professeur (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(177,Level.ORANGE,Type.THEORY,"Que signifie 'Otagaï ni rei'","Saluez-vous entre élèves (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(178,Level.ORANGE,Type.THEORY,"Que signifie 'Shomen ni rei'","Saluez la mémoire des ancêtres (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(179,Level.YELLOW,Type.THEORY,"Que signifie Kiritsu","Relevez-vous (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(180,Level.YELLOW,Type.THEORY,"Que signifie Hajime","Commencez (l'exercice, le combat…)","",""));
        this.questionList.add(new Question(181,Level.YELLOW,Type.THEORY,"Que signifie Matte","Arrêtez-vous (ordre très important)","",""));
        this.questionList.add(new Question(182,Level.GREEN,Type.THEORY,"Que signifie ONEGAISHIMASU","« s’il vous plait » formule de politesse « Voulez-vous travailler avec moi ? SVP »","",""));
        this.questionList.add(new Question(183,Level.GREEN,Type.THEORY,"Que signifie ARIGATO GOZAIMASU","merci beaucoup (pour vos conseils, pour avoir travaillé avec moi, pour le cours, etc…)","",""));
        this.questionList.add(new Question(184,Level.YELLOW,Type.THEORY,"Que signifie ICHI, NI, SAN, SHI (YON), GO, ROKU, SHICHI (NANA), ACHI, KYU, JUU ?","Compte de 1 à 10 ","",""));
        this.questionList.add(new Question(185,Level.YELLOW,Type.THEORY,"Que signifie REI ?","Saluez","",""));
        this.questionList.add(new Question(186,Level.ORANGE,Type.PRACTICE,"Parade à deux niveaux","","",""));
        this.questionList.add(new Question(187,Level.BLUE,Type.PRACTICE,"Technique de 'viens donc' par clé et étranglement","","",""));
        this.questionList.add(new Question(188,Level.BLUE,Type.PRACTICE,"Coup de pied sauté circulaire (Mawashi Tobi Geri)","","",""));
        this.questionList.add(new Question(189,Level.ORANGE,Type.PRACTICE,"Ryote Ippon Seoi Nage","","",""));
        this.questionList.add(new Question(190,Level.BLACK,Type.PRACTICE,"Yoko wakare","","",""));
        this.questionList.add(new Question(191,Level.BLACK,Type.PRACTICE,"Sutemi arrière par les hanches","","",""));
        this.questionList.add(new Question(192,Level.GREEN,Type.PRACTICE,"Immobilisation par clé de bras en croix (ude juji gatame)","","",""));
        this.questionList.add(new Question(193,Level.GREEN,Type.PRACTICE,"Juji gatame","","",""));
        this.questionList.add(new Question(194,Level.GREEN,Type.PRACTICE,"immobilisation par Ude Garami","","",""));
        this.questionList.add(new Question(195,Level.GREEN,Type.PRACTICE,"Différentes formes de clé de pouce","","",""));
        this.questionList.add(new Question(196,Level.ORANGE,Type.PRACTICE,"Nekko ahshi dachi","","https://drive.google.com/file/d/172c9A0GCUnp36MKm8pD4SCQri1ixwtxQ/view?usp=sharing",""));
        this.questionList.add(new Question(197,Level.ORANGE,Type.PRACTICE,"Marteau de fer - tetsui","","",""));
        this.questionList.add(new Question(198,Level.BLUE,Type.PRACTICE,"Coup de pied circulaire inversé - Ura Mawashi Geri","","",""));
        this.questionList.add(new Question(199,Level.ORANGE,Type.PRACTICE,"Frappe en revers de poing (uraken uchi)","","",""));
        this.questionList.add(new Question(200,Level.YELLOW,Type.PRACTICE,"Kihon waza - déplacements seuls","","",""));
        this.questionList.add(new Question(201,Level.YELLOW,Type.PRACTICE,"Kihon waza - déplacements et parades","","",""));
        this.questionList.add(new Question(202,Level.ORANGE,Type.PRACTICE,"Kihon waza - déplacements et parades à deux","","",""));
        this.questionList = Collections.unmodifiableList(this.questionList);

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

}
