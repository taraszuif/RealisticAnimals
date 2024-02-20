package me.zuif.rean.api.compat;

public enum Version {
    v1_17_R1, UNKNOWN;

    public static Version parse(String version) {
        Version result = Version.UNKNOWN;
        switch (version) {
            case "v1_17_R1" -> result = Version.v1_17_R1;
        }
        return result;
    }

}
