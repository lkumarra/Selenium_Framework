package com.org.bank.constants;

import com.org.bank.modals.CredModal;
import lombok.Getter;
import lombok.Setter;

public final class CredModalContext {

    private CredModalContext() {
    }

    @Getter
    @Setter
    private static CredModal credModal;

}
