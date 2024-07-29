package com.org.bank.constants;

import com.org.bank.modals.CredModal;

public final class CredModalContext {

    private CredModalContext() {
    }
    private static CredModal credModal;

    /**
     * This method is used to set the CredModal instance in the CredModalContext class.
     * It is a static method, meaning it belongs to the CredModalContext class and not to any instance of this class.
     * The method takes a CredModal object as a parameter and assigns it to the static CredModal variable in the CredModalContext class.
     *
     * @param credModal The CredModal instance to be set in the CredModalContext.
     */
    public static void setCredModal(CredModal credModal) {
        CredModalContext.credModal = credModal;
    }

    /**
     * This method is used to get the CredModal instance from the CredModalContext class.
     * It is a static method, meaning it belongs to the CredModalContext class and not to any instance of this class.
     * The method does not take any parameters and returns the static CredModal variable in the CredModalContext class.
     *
     * @return The CredModal instance stored in the CredModalContext.
     */
    public static CredModal getCredModal() {
        return credModal;
    }
}
