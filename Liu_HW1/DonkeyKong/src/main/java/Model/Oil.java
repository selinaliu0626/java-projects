package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//will generate two constructors(no arguments/all arguments)
public class Oil {
    private int x;
    private int y;
}
