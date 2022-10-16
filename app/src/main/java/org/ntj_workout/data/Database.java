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


        this.questionList.add(new Question(Level.WHITE,Type.PRACTICE,"Ceci est un test pour l'affichage d'une image depuis Drive","","https://drive.google.com/uc?export=view&id=172c9A0GCUnp36MKm8pD4SCQri1ixwtxQ",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 1 par atemi","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 2 par atemi","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 3 par atemi  (deux formes)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 4 par atemi (deux formes)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 5 par atemi","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 6 par atemi","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 7 par atemi","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Base 8 par atemi ( (deux formes)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Faire son noeud de ceinture","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Salut debout","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Position ZENKUTSU DACHI","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Position KOKUTSU DACHI","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Position SHIKO DACHI","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Salut à genou","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Clé de bras sous l'aisselle - waki gatame","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Clé de bras tendue sur l'épaule - Tenbim Gatame","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Mise en garde classique","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Mise en garde garde mixte","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Mise en garde garde « kempo »","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Esquive en avançant (IRIMI)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Esquive en reculant (NAGASHI)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Esquive latérale (HIRAKI)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Esquive rotative (IRIMI SENKAI)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Parade basse","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Parade niveau moyen","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Parade haute","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Clé de bras en croix (juji ude gatame)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Clé de poignet vers l'extérieur (kote gaeshi)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"projection -en rentrant- (mukae daoshi ou irimi nage)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Grand fauchage de la jambe extérieure (O soto gari)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Changement de garde en avançant et en reculant","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Mise en YOI","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Demi tour en garde","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Chute avant roulée à droite","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chute avant plaquée à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Chute arrière roulée à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Chute arrière plaquée","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Ordres japonais donnés par le sampai en début de cours?","Seiza / Otagaï ni rei / Senseï ni rei / shomen ni rei / Kiritsu","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie DACHI ?","Position","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Coup de pied direct (mae geri)","","","https://drive.google.com/uc?export=view&id=1LT1GWGbBvYtjxeUiO3dcSR1v__hT5qKC"));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Coup de poing direct en avançant (oi tsuki)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Coup de poing sen opposition (gyaku tsuki)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Coup de coude (empi uchi)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Fouetté des doigts (me uchi)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Coup de genou (hiza geri)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Chute latérale à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Garde au sol","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 1 par clé","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie TAI-SABAKI ?","Esquive","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 1 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 2 par clé","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 2 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 3 par clé","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie TORI ?","Le défenseur, celui qui exécute la technique","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie UKE ?","L’attaquant, celui qui la subit la technique","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie WAZA ?","Techniques","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 3 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 4 par clé","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 4 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 5 par clé","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 5 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 6 par clé","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 6 par projection","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie ATEMI ?","Coup frappé par une partie sensible du corps","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 7 par clé","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 7 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 8 par clé","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"base 8 par projection","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie UKEMI ?","Chute","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Frappe en sabre haut diagonal (yokomen uchi)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Frappe en sabre de main haut (shomen  uchi)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Diférentes formes de coup de coude (empi uchi)","Debout pieds écartés et parallèles","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Coup de pied circulaire (mawashi geri)","Position du cavalier","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chute avant roulée à gauche","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chute avant plaquée à gauche","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chute arrière roulée à gauche","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Clé de main inversée (kote gatame)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Clé en enroulement (ude garami)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Clé de poignet en torsion vers l'extérieur par le pouce (gyaku kote gaeshi)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Clé de poignet en torsion vers l'intérieur (yuki chigae = kote hineri)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Différentes formes de clé de pouce","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Clé de -bras en Z- (kote kudaki)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Crochetage de jambe intérieure – Ko uchi gari","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chargement de hanche – O goshi","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chargement au cou – kubi nage","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Chargement d’épaule à l’intérieur – ippon seoi nage","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Ramassement de jambes – do gaeshi","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Projection par la tête – hachi mawashi","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Projection par le bras enroulé – shiho nage","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Projection par l’épaule à l’extérieur –(tenbim nage ou ude kakae)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"chute lattérale plaquée à gauche","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Clé de main (2 formes) - kote gatame","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Clé de bras en flexion sur l’épaule (début de shiho nage)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Clé de bras en flexion dans le dos (ude gatame)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Clé sur le bras tendu (ude gatame)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Clé de bras en enroulement (3 formes) – ude garami","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Clé sur le ventre – Hara gatame","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Balayage de pied – de hachi barai","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Sutemi avant – tomoe nage","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Sutemi arrière (tani otoshi)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Sutemi arrière en ciseau de jambe (kami basami)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Projection en renversement arrière (do gaeshi)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE," Sutemi latéral – yoko sutemi","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Chargement sur les deux épaules (debout) – kata Guruma","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Chargement sur les deux épaules (un genou au sol) – Tamara Guruma","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Immobilisation par clé de poignet bras tendu, uke sur le dos (tâte gassho gatame)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Immobilisation par clé en torsion de main, uke sur le dos (kote gaeshi gatame)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Immobilisation par clé en flexion de bras, uke sur le dos (ude garami)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Immobilisation par clé en flexion de bras, uke sur le ventre (kote mawhashi gatame)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Immobilisation par clé en triangle, uke sur le ventre (sankaku gatame)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Immobilisation par clé en pression au coude (uke sur le ventre)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Renversement du corps en barrage (harai goshi)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Pression sur point sensible sous les oreilles","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Grand fauchage de la jambe extérieur main sur le visage de uke","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Etranglement à main nu (hadaka jime)","","","https://drive.google.com/uc?export=view&id=1XOv-JA8GokcFIl7UbNeQVfGt0s0dX9vh"));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Projection par rammassement de jambe (sukui nage)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Projection de la -rame- (robuse ou ude osae)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Chute latérale roulée à gauche et à droite","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.THEORY,"Qu'est ce qu'un KIHON IPPON KUMITE ?","Assaut conventionnel: une attaque contrée par une seule défense et ou contre-attaque","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie RITSUREI ?","Salut debout","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie ZAREI ?","Salut à genoux","",""));
        this.questionList.add(new Question(Level.GREEN,Type.THEORY,"Que signifie REISHIKI ?","Cérémonial","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie SEIZA ?","Position à genoux","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Qu'exprime le mot YAME ?","Fin de l’exercice retour en YOI (toujours vigilent)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Qu'exprime le mot YASSME ?","Se décontracter (intervient YAME)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Qu'exprime le mot YOI ?","Position et état mental concentré et vigilant","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie KIMONO ?","littéralement  -chose que l'on porte sur soi-.","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie KEIKO GI ?","Vêtement d’entraînement-","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre yama arashi","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Technique de -viens donc- par clé en bec de cygne","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Technique de -viens donc- par le pouce","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Technique de -viens donc- par clé de bras en croix","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"étranglement nu (hadaka jime)","","","https://drive.google.com/uc?export=view&id=1XOv-JA8GokcFIl7UbNeQVfGt0s0dX9vh"));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"étranglement en enroulant par la manche (sode guruma jime)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"étranglement en contrôlant l'épaule (kata ha jime)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"étranglement avec le revers (eri jime)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Coup de pied direct sauté (Mae Tobi Geri)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Coup de pied en ruade (Ushiro Geri)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Atemi retournés - Ushiro Ura Mawashi Geri","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Immobilisation par clé de cheville en croix, uke sur le dos (kata ashi hishigi)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Immobilisation par clé de jambe fléchie, uke sur le ventre (hiza hishigi)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Immobilisation par clé de cheville en torsion vers l'intérieur, uke sur le dos (ashi dori garami)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Garde au sol","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Tori au sol, uke debout devant (plusieurs formes d’atemi et de ciseaux)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Uke à cheval sur Tori","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Uke attaque entre les jambes de Tori","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Uke est derrière Tori","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Tori renverse Uke","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Crochet (Mawashi Tsuki)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Upercut","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"projection extérieure (soto tenkai)","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"projection par le col et par l'arrière (ushiro eri kata nage)","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"projection lattérale par poussée du coude (ude oshiage)","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"projection arrière en poussée du menton (ago ate nage)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"projection par traction de la manche (uki otoshi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"projection par traction de la manche et du cou (kubi uki otoshi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"sutemi par le cou (kubi nage sutemi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"projection par par levier de l'avant bras au genou (ashi otoshi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"projection par fauchage bras tendu (ude maki soto gari)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"sutemi (tobi nori sutemi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"projection enroulée par la manche (sode guruma otoshi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Yoko sutemi (yoko otoshi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"sutemi dans l'angle (sumi gaeshi)","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"kubi mawashi sutemi","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"yoko guruma","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"ude gake sutemi","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.THEORY,"Que signifie Kamiza?","Place d'honneur","",""));
        this.questionList.add(new Question(Level.BROWN,Type.THEORY,"Qu'est-ce qu'un Kuatsu?","Une technique de soin ou de réanimation par percussion de zone particulères du corps","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"eri tori sutemi","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Yoko tomoe","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"atama guruma","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie KAMAE ?","En garde !","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Quels sont les ordres japonais donnés par le sampai en fin de cours?","Seiza / Shomen ni rei / Senseï ni rei / Otagaï ni rei / Kiritsu","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie Yoi ?","Position de vigilance physique et mentale et un ordre donné par le professeur de prendre la position Yoi","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie SEIZA ?","Position agenouillée et ordre donné par le professeur de se mettre à genou","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie -Senseï ni rei-","Saluez le professeur (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie -Otagaï ni rei-","Saluez-vous entre élèves (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.THEORY,"Que signifie -Shomen ni rei-","Saluez la mémoire des ancêtres (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie Kiritsu","Relevez-vous (ordre donné par le sampai lors du cérémonial de début et fin de cours)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie Hajime","Commencez (l'exercice, le combat…)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie Matte","Arrêtez-vous (ordre très important)","",""));
        this.questionList.add(new Question(Level.GREEN,Type.THEORY,"Que signifie ONEGAISHIMASU","« s’il vous plait » formule de politesse « Voulez-vous travailler avec moi ? SVP »","",""));
        this.questionList.add(new Question(Level.GREEN,Type.THEORY,"Que signifie ARIGATO GOZAIMASU","merci beaucoup (pour vos conseils, pour avoir travaillé avec moi, pour le cours, etc…)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie ICHI, NI, SAN, SHI (YON), GO, ROKU, SHICHI (NANA), ACHI, KYU, JUU ?","Compte de 1 à 10 ","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie REI ?","Saluez","",""));

        this.questionList = Collections.unmodifiableList(this.questionList);

        return this;
    }

    public Revision start(Level level, Type type) {

        if (this.questionList == null) {
            throw new IllegalStateException("call init first");
        }

        List<Question> newList = new LinkedList<>(this.questionList);
        if (level != Level.BLACK) {
            newList = newList.stream().filter(question -> {
                return question.getLevel().ordinal() <= level.ordinal();
            }).collect(Collectors.toList());
        }
        if (type != Type.BOTH) {
            newList = newList.stream().filter(question -> {
                return question.getType() == type;
            }).collect(Collectors.toList());
        }

        if (newList.isEmpty()) {
            return null;
        }
        Collections.shuffle(newList);
        return new Revision(newList);
    }

}
