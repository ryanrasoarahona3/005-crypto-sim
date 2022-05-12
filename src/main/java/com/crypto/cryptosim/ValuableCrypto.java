package com.crypto.cryptosim;

/**
 * Pour l'ajout des fonctionnalités, j'ai préféré opté pour
 * une héritage afin que le code soit bien aéré et organisé
 */
public class ValuableCrypto extends Crypto{

    /**
     * La valeur actuelle du crypto
     * Cette variable est automatiquement gérée par TickManager
     */
    private int value;

    public int getValue() {
        return value;
    }

    /**
     * Cette fonction ne peut être appelé qu'une seule fois lors de la création du crypto
     * Sinon, une erreur sera levée
     *
     * Normalement, ce sera au tour de TickManager qui gèrera automatiquement les valeurs
     * de chaque crypto
     */
    public void initValue(int initialValue){
        /**
         * La modification de la valeur d'un crypto doit être automatiquement fait par
         * TickManager
         */
        this.value = initialValue;
        (TickManager.getInstance()).register(this);
    }
}
