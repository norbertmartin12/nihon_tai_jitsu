package org.ntj_workout.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

    private List<Question> questionList;

    public Database init() {
        this.questionList = new LinkedList<>();

        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 1 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 1 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 1 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 2 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 2 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 2 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 3 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 3 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 3 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre les deux formes de la base 4 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 4 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 4 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 5 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 5 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 5 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 6 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 6 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 6 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 7 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 7 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 7 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la base 8 par atémi (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre la base 8 par clé (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre les deux formes de la base 8 par projection (droite et gauche)","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre le 1er kata seul","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre le 2ème kata seul","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"Démontre le 3ème kata seul","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre le No Kata Shodan (1er kata à deux), ou sa défense seul","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre le No Kata Nidan (2ème kata à trois), ou sa défense seul","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"Démontre le No Kata Sandan (3ème kata à deux), ou sa défense seul","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre les 4 premières attaques et défenses du Kihon Kata","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Démontre toutes les attaques et défenses du Kihon Kata","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Démontre le Hyori No Kata","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Démontre le No Kata Yodan","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Démontre le No Kata Godan","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Démontre le Ju Ni No Kata","","",""));
        this.questionList.add(new Question(Level.BLACK,Type.PRACTICE,"Démontre le Dai Ni No Kata","","",""));
        this.questionList.add(new Question(Level.WHITE,Type.PRACTICE,"Démontre le Tai Sabaki No Kata","Voilà une démo (source: youtube)","","https://www.youtube.com/watch?v=4yMM05IvF1Y"));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre le nouage de la ceinture et la bonne présentation du keikogi","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre le salut debout et le salut à genou","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Quels sont les ordres japonais entendus pendant le cours et leur signification","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie DACHI ?","Position","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre HEIKO DACHI","Debout pieds écartés et parallèles","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre ZENKUTSU DACHI","Fente avant : jambe avant fléchie, jambe arrière tendue","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre KOKUTSU DACHI","Fente arrière : 70% du poids du corps sur la jambe arrière","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre KIBA DACHI","Position du cavalier","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre FUDO DACHI","Position équilibrée de combat (entre Zen kutsu et Kokutsu)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre NEKO ASHI DACHI","Position du chat","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie KAMAE ?","Garde","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre HIDARI KAMAE (garde à gauche)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre MIGI KAMAE (garde à droite)","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la garde classique","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la garde mixte","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la garde kempo","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que Signifie TAI SABAKI ?","Esquive","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre l'esquive en avançant (IRIMI) à gauche et à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre l'esquive en reculant (NAGASHI) à gauche et à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre l'esquive latérale (HIRAKI) à gauche et à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre l'esquive rotative (IRIMI SENKAI) à gauche et à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie TORI et UKE ?","TORI signifie attaquant, celui qui exécute la technique. UKE signifie défenseur, celui qui la subit.","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie UKE WAZA ?","Technique de défense","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre GEDAN BARAI","Défense basse par un mouvement de balayage avec le bras","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre JODAN AGE UKE","Défense haute par un mouvement remontant avec le bras","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre SOTO UDE UKE","Défense avec le bras dans un mouvement de l’extérieur vers l’intérieur","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre UCHI UDE UKE","Défense avec le bras dans un mouvement l’intérieur vers l’extérieur","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre SHUTO UKE","Défense avec le tranchant de la main","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre JUJI UKE (ou KOSA UKE)","Défense double avec les bras croisés - croisillons","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre MOROTE UKE","Défense double, bras arrière en protection","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie ATEMI ?","Coup frappé par une partie du corps","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie ATEMI WAZA ?","Technique de coup (frappé par une partie du corps)","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la position YOI","Un dessin vaut mieux qu'une longue description (source: image internet)","https://ecolekaratemarais.fr/wp-content/uploads/2020/05/Yoi.jpg",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre un demi tour","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre les 8 TAI SABAKI avec parade et riposte","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.THEORY,"Que signifie UKEMI ?","Chute","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre une chute avant roulée à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre une chute avant plaquée à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre une chute arrière roulée à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre une chute arrère plaquée à droite","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Réalise un randori ATEMI de maximum 1'30. Avec un partenaire les deux attaquent et défendent alternativement. Seul, démontre la même chose (technique du shadow)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre une chute latérale roulée, à gauche et à droite. Marque une garde au sol après avoir effectué la chute puis relève toi dans la garde de ton choix","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Démontre une chute latérale plaquée, à gauche et à droite. Marque une garde au sol après avoir effectué la chute puis relève toi dans la garde de ton choix","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre une chute avant roulée à gauche","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre une chute avant plaquée à gauche","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre une chute arrière roulée à gauche","","",""));
        this.questionList.add(new Question(Level.ORANGE,Type.PRACTICE,"Démontre une chute arrère plaquée à gauche","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Réalise un randori type NIHON TAI JITSU de maximum 1'30. Avec un partenaire les deux attaquent et défendent alternativement. Seul, démontre la même chose (technique du shadow)","","",""));
        this.questionList.add(new Question(Level.GREEN,Type.PRACTICE,"Réalise un randori en cercle de type NIHON TAI JITSU de minimum 5 attaquants. Seul, démontre la même chose (technique du shadow)","","",""));
        this.questionList.add(new Question(Level.BLUE,Type.PRACTICE,"Réalise un randori de technique au sol de maximum 1'30. Avec un partenaire les deux attaquent et défendent alternativement. Seul, démontre la même chose (technique du shadow)","","",""));
        this.questionList.add(new Question(Level.BROWN,Type.THEORY,"Qu'est ce qu'un KIHON IPPON KUMITE ?","Le Kihon Ippon Kumite est un assaut conventionnel basé sur une attaque contrée par une seule défense et ou contre-attaque. Il est composé des 5 attaques : Shuto Shomen Uchi ; Oi tsuki chudan ; Mae geri ; Mawashi geri chudan ; Yoko geri chudan. Cet assaut se déroule de la manière suivante : Les deux candidats sont placés à une distance de 1 mètre l’un de l’autre et de profil par rapport au jury ; Uke se met en garde mixte à gauche pour les attaques à droite puis à droite pour les attaques à gauche ; Tori reste en position yoi ; Uke annonce son attaque et après un moment de concentration, lance celle-ci avec le plus de conviction et de détermination possible ; Tori effectue une technique de défense simple par atemi, luxation, projection ou strangulation. Après chaque attaque, Tori se repositionne en position yoi et Uke en position de garde prêt à effectuer une autre attaque. Uke effectuera les 5 attaques à droite puis les 5 attaques à gauche. L’attaque s’effectuera toujours avec le bras ou la jambe arrière.","",""));
        this.questionList.add(new Question(Level.BROWN,Type.PRACTICE,"Démontre un KIHON IPPON KUMITE. Seul, démontre les attaques et les défenses en méthode shadow","","",""));
        this.questionList.add(new Question(Level.YELLOW,Type.PRACTICE,"Démontre la position HACHIJI DACHI","Un dessin vaut mieux qu'une longue description (source: image drive)","https://drive.google.com/uc?export=view&id=172c9A0GCUnp36MKm8pD4SCQri1ixwtxQ",""));

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
