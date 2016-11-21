package trimps.eid.zl.CDI;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class CDIProducerFactory {

	@Produces
	Logger getLogger(InjectionPoint injectionPoint) {
		return new BizSeqIDLogger(Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName()));
	}

}
