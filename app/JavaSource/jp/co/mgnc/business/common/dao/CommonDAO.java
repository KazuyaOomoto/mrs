package jp.co.mgnc.business.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jp.co.mgnc.business.common.exception.DaoException;

/**
 * DAOクラスのための共通クラスです。データベースの接続や切断機能を提供します。
 * @author Oomoto Kazuya
 * @version 1.0
 */
public class CommonDAO {

	/**
	 * Connectionオブジェクト。
	 */
	protected Connection conn;

	/**
	 * データベースをクローズする。
	 * @throws DaoException
	 */
	protected void closeConnection() throws DaoException {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	/**
	 * データベースコネクションを取得する。
	 * @throws DaoException
	 */
	protected void getConnection() throws DaoException {

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/mrs";
			conn = DriverManager.getConnection(url, "postgres", "postgres");

		} catch (ClassNotFoundException e) {
			throw new DaoException();
		} catch (SQLException e) {
			throw new DaoException();
		}

	}

}
