package leapfrog.dataloader;

import java.util.ArrayList;
import java.util.List;

public record TabResult(String tabName, boolean success, List<String> errors) {
    public TabResult addError(String error) {
        this.errors.add(error);

        //Create a new record object with success=false and updated errors list
        return new TabResult(tabName, false, errors);
    }

    public TabResult(String tabName) {
        this(tabName, true,new ArrayList<>());
    }
}
