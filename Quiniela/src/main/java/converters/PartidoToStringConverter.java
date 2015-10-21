package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Partido;

@Component
@Transactional
public class PartidoToStringConverter implements Converter<Partido, String> {

    @Override
    public String convert(Partido partido) {
        String result;

        if (partido == null)
            result = null;
        else
            result = String.valueOf(partido.getId());

        return result;
    }

}


