package parser.big01.model;

import parser.big01.vo.Vacancy;

import java.util.List;

/**
 * Created by yehor on 22.02.2016.
 */
public interface Strategy
{
    List<Vacancy> getVacancies(String searchString);
}
