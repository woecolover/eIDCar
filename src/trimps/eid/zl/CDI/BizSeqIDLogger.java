package trimps.eid.zl.CDI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BizSeqIDLogger extends Logger {
	/**几种消息的优先级**/

	Logger logger;

	public BizSeqIDLogger(Logger logger) {
		super(logger.getName(), logger.getResourceBundleName());
		this.logger = logger;
	}

	public BizSeqIDLogger(String arg0, String arg1) {
		super(arg0, arg1);
	}

	private String bizSeqID() {
		return "[" + ThreadContext.get("BizSeqID") + "]";
	}

	@Override
	public void fine(String arg0) {
		logger.fine(bizSeqID() + arg0);
	}

	@Override
	public void finer(String arg0) {
		logger.finer(bizSeqID() + arg0);
	}

	@Override
	public void finest(String arg0) {
		logger.finest(bizSeqID() + arg0);
	}

	@Override
	public void info(String arg0) {
		logger.info(bizSeqID() + arg0);
	}

	@Override
	public void log(Level arg0, String arg1) {
		logger.log(arg0, bizSeqID() + arg1);
	}

	@Override
	public void severe(String arg0) {
		logger.severe(bizSeqID() + arg0);
	}

	@Override
	public void warning(String arg0) {
		logger.warning(bizSeqID() + arg0);
	}

}
