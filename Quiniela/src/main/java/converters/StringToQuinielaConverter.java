package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Quiniela;
import repositories.QuinielaRepository;

@Component
@Transactional
public class StringToQuinielaConverter implements Converter<String, Quiniela> {

    @Autowired
    QuinielaRepository quinielaRepository;

    @Override
    public Quiniela convert(String text) {
    	Quiniela result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = quinielaRepository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}
