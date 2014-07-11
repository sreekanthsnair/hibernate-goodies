package org.java.hibernate.support;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SequenceIdGenerator implements IdentifierGenerator {

	private static final String SEQUENCE_PREFIX = "seq_";

	public Serializable generate(final SessionImplementor session, final Object object) throws HibernateException {
		String sequenceName = SEQUENCE_PREFIX;
		if (object.getClass().isAnnotationPresent(Table.class)) {
			sequenceName += object.getClass().getAnnotation(Table.class).name();
		} else {
			sequenceName += object.getClass().getSimpleName().toLowerCase();
		}
		try {
			try {
				return getNextSequence(session, sequenceName);
			} catch (final Exception e) {
				return createAndGetNextSequence(session, sequenceName);
			}

		} catch (final Exception e) {
			throw new RuntimeException("Error occurred while generating ID", e);
		}
	}

	private Serializable getNextSequence(final SessionImplementor session, final String sequenceName) throws SQLException {
		final PreparedStatement ps = session.connection().prepareStatement("SELECT nextval (?) as nextval");
		ps.setString(1, sequenceName);
		final ResultSet rs = ps.executeQuery();
		Serializable sequence = 0;
		if (rs.next()) {
			sequence = rs.getLong("nextval");
		}
		closeResource(ps, rs);
		return sequence;
	}

	private Serializable createAndGetNextSequence(final SessionImplementor session, final String sequenceName) throws SQLException {
		final PreparedStatement ps = session.connection().prepareStatement("create sequence " + sequenceName);
		ps.executeUpdate();
		closeResource(ps, null);
		return getNextSequence(session, sequenceName);
	}

	private void closeResource(final PreparedStatement ps, final ResultSet rs)
			throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
	}

}
