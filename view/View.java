package parser.big01.view;

import parser.big01.Controller;
import parser.big01.vo.Vacancy;

import java.util.List;

/**
 * Created by yehor on 23.02.2016.
 */
public interface View
{
    void update(List<Vacancy> vacancies);

    void setController(Controller controller);
}
