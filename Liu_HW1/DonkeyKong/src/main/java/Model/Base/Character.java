package Model.Base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// will generate getters and setters
@NoArgsConstructor
@AllArgsConstructor
// different ways of constructing
//@NoArgsConstructor means  constructor with no arguments
//@AllArgsConstructor means constructor with all arguments
//generated two ways of constructor when compile successfully
public abstract class Character {
    private String name;
    private double x;
    private double y;
}
