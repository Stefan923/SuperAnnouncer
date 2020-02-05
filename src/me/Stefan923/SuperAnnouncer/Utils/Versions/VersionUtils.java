package me.Stefan923.SuperAnnouncer.Utils.Versions;

import org.bukkit.Bukkit;

public interface VersionUtils {

    default Version getVersionObject() {
        String version = getServerVersion();

        if (version.equals("v1_8_R1")) {
            return new Version_1_8_R1();
        } else if (version.equals("v1_8_R2")) {
            return new Version_1_8_R2();
        } else if (version.equals("v1_8_R3")) {
            return new Version_1_8_R3();
        } else if (version.equals("v1_9_R1")) {
            return new Version_1_9_R1();
        } else if (version.equals("v1_9_R2")) {
            return new Version_1_9_R2();
        } else if (version.equals("v1_10_R1")) {
            return new Version_1_10_R1();
        } else if (version.equals("v1_11_R1")) {
            return new Version_1_11_R1();
        } else if (version.equals("v1_12_R1")) {
            return new Version_1_12_R1();
        } else if (version.equals("v1_13_R1")) {
            return new Version_1_13_R1();
        } else if (version.equals("v1_13_R2")) {
            return new Version_1_13_R2();
        } else if (version.equals("v1_14_R1")) {
            return new Version_1_14_R1();
        } else if (version.equals("v1_15_R1")) {
            return new Version_1_15_R1();
        }
        return null;
    }

    default String getServerVersion() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

}
