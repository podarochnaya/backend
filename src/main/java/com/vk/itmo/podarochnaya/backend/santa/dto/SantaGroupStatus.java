package com.vk.itmo.podarochnaya.backend.santa.dto;

public enum SantaGroupStatus {
    ACTIVE(1),
    INACTIVE(0);

    private final int status;

    SantaGroupStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static SantaGroupStatus fromStatus(int status) {
        for (SantaGroupStatus currentStatus : values()) {
            if (currentStatus.status == status) {
                return currentStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status " + status);
    }
}
