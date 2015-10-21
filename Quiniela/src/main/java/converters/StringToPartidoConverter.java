package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Partido;
import repositories.PartidoRepository;

@Component
@Transactional
public class StringToPartidoConverter implements Converter<String, Partido> {

    @Autowired
    PartidoRepository partidoRepository;

    @Override
    public Partido convert(String text) {
    	Partido result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = partidoRepository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }

}

