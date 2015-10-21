package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Quiniela;

@Component
@Transactional
public class QuinielaToStringConverter implements Converter<Quiniela, String> {

    @Override
    public String convert(Quiniela quiniela) {
        String result;

        if (quiniela == null)
            result = null;
        else
            result = String.valueOf(quiniela.getId());

        return result;
    }

}

