package tn.arteco.models;

import java.util.Date;

public class Participation {

private NonArtiste participant;
private Evenement evenement;

    public Participation(NonArtiste participant, Evenement evenement) {
        this.participant = participant;
        this.evenement = evenement;
    }

    public NonArtiste getParticipant() {
        return participant;
    }

    public void setParticipant(NonArtiste participant) {
        this.participant = participant;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "participant=" + participant +
                ", evenement=" + evenement +
                '}';
    }
}
