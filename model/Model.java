package parser.big01.model;

import parser.big01.view.View;
import parser.big01.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yehor on 23.02.2016.
 */
public class Model
{
    private View view;

    private Provider[] providers;

    public Model(View view, Provider... providers)
    {
        if (view == null || providers == null)
            throw new IllegalArgumentException();
        if(providers.length == 0)
            throw new IllegalArgumentException();
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city)
    {
        List<Vacancy> list = new ArrayList<>();
       for (Provider provider : providers)
       {
           list.addAll(provider.getJavaVacancies(city));
       }
        view.update(list);
    }
}
