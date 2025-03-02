package leapfrog.dataobjects;

import java.util.List;

public record TabConfig(String tabName, int startPage, int endPage, String csvFilePath) {
}
