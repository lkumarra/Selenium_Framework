package com.org.bank.constants;

import com.org.bank.modals.CredModal;

public class CredModalContext {
    private static CredModal credModal;

    public static void setCredModal(CredModal credModal) {
        CredModalContext.credModal = credModal;
    }

    public static CredModal getCredModal() {
        return credModal;
    }
}
