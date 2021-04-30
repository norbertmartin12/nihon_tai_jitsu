package org.ntj_workout.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {

    private List<Question> questionList;

    public Database init() {
        this.questionList = new LinkedList<>();

        this.questionList.add(new Question(Level.WHITE, Type.PRACTICE, "Démontre le Tai Sabaki No Kata ?", "https://www.youtube.com/watch?v=4yMM05IvF1Y"));

        this.questionList.add(new Question(Level.YELLOW, Type.PRACTICE, "Démontre le salut debout et le salut à genoux ?", ""));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Quels sont les ordres japonais entendus pendant le cours et leur signification ?", ""));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Que signifie DACHI ?", "Position"));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Que signifie KAMAE ?", "Garde"));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Que Signifie TAI SABAKI ?", "Esquive"));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Que signifie UKE WAZA ?", "Technique de défense"));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Que signifie TORI et UKE ?", "TORI signifie attaquant, celui qui exécute la technique.\nUKE signifie défenseur, celui qui la subit."));
        this.questionList.add(new Question(Level.YELLOW, Type.THEORY, "Que Signifie TAI SABAKI ?", "Esquive"));
        this.questionList.add(new Question(Level.YELLOW, Type.PRACTICE, "Démontre la position YOI", "https://ecolekaratemarais.fr/wp-content/uploads/2020/05/Yoi.jpg"));
        // FIXME drive share impossible this.questionList.add(new Question(Level.YELLOW, Type.PRACTICE, "Démontre la position HACHIJI DACHI", "https://drive.google.com/file/d/172c9A0GCUnp36MKm8pD4SCQri1ixwtxQ/view"));

        this.questionList.add(new Question(Level.ORANGE, Type.PRACTICE, "Démontre HEIKO DACHI", "Debout pieds écartés et parallèles"));

        this.questionList.add(new Question(Level.GREEN, Type.PRACTICE, "Démontre le 2ème kata seul", ""));

        this.questionList.add(new Question(Level.BLUE, Type.PRACTICE, "Démontre toutes les attaques et défenses du Kihon Kata", ""));

        this.questionList.add(new Question(Level.BROWN, Type.THEORY, "Qu'est ce qu'un KIHON IPPON KUMITE ?", "Le Kihon Ippon Kumite est un assaut conventionnel basé sur une attaque contrée par une seule " +
                "défense et ou contre-attaque. Il est composé des 5 attaques : " +
                "Shuto Shomen Uchi ; Oi tsuki chudan ; Mae geri ; Mawashi geri chudan ; Yoko geri chudan.\n\n" +
                "Cet assaut se déroule de la manière suivante :\n" +
                "Les deux candidats sont placés à une distance de 1 mètre l’un de l’autre et de profil par\n" +
                "rapport au jury ; Uke se met en garde mixte à gauche pour les attaques à droite puis à droite pour les attaques à gauche ; Tori reste en position yoi ;\n" +
                "Uke annonce son attaque et après un moment de concentration, lance celle-ci avec le plus\n" +
                "de conviction et de détermination possible ;\n" +
                "Tori effectue une technique de défense simple par atemi, luxation, projection ou strangulation.\n" +
                "Après chaque attaque, Tori se repositionne en position yoi et Uke en position de garde prêt à\n" +
                "effectuer une autre attaque.\n\n" +
                "Uke effectuera les 5 attaques à droite puis les 5 attaques à gauche.\n" +
                "L’attaque s’effectuera toujours avec le bras ou la jambe arrière."));
        this.questionList.add(new Question(Level.BROWN, Type.PRACTICE, "Démontre un KIHON IPPON KUMITE. Seul, démontre les attaques et les défenses en méthode shadow", ""));

        this.questionList.add(new Question(Level.BLACK, Type.PRACTICE, "Démontre le Hyori No Kata", ""));
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
