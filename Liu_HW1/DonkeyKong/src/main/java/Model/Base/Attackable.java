package Model.Base;

import Model.Cask;

import java.util.List;

public interface Attackable {
    void attack(List<Cask> casks);
}
