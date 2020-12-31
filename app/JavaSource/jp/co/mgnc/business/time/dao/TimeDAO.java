package jp.co.mgnc.business.time.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.mgnc.business.common.dao.CommonDAO;
import jp.co.mgnc.business.common.exception.DaoException;
import jp.co.mgnc.business.time.dto.Time;

/**
 * 時間帯情報を操作するDAOクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 */
public class TimeDAO extends CommonDAO {

	//全時間帯情報を取得するSQL
	private static final String SELECT_ALL = "select * from m_time";
	//time_cdから時間帯情報を取得するSQL
	private static final String SELECT_BY_KEY = "select * from m_time where time_cd=?";

    /**
	 * 全時間帯情報を取り出すメソッドです。
	 * @param なし
	 * @return 全時間帯情報
	 * @throws DaoException
	 */
	public ArrayList<Time> getTimeAllList() throws DaoException{
		
		//戻り値を宣言する。
		ArrayList<Time> ret = new ArrayList<>();
		
		try {
			
			//コネクションを取得する。
			getConnection();
			//SQL文を設定し、従業員番号、パスワードをセットする。
			PreparedStatement statement = conn.prepareStatement(SELECT_ALL);
			
			//SQL文を実行し、Timeオブジェクトを取得する。
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				Time time = new Time();
				time.setTimeCd(resultSet.getInt("time_cd"));
				time.setTimeName(resultSet.getString("time_name"));
				ret.add(time);
			}
			
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			closeConnection();
		}
		return ret;
	}

    /**
	 * 時間帯コードから時間帯情報を取り出すメソッドです。
	 * @param timeCd 時間帯コード
	 * @return 時間帯情報
	 * @throws DaoException
	 */
	public Time selectByTimeCd(int timeCd) throws DaoException{
		
		//戻り値を宣言する。
		Time ret = null;
		
		try {
			
			//コネクションを取得する。
			getConnection();
			//SQL文を設定し、従業員番号、パスワードをセットする。
			PreparedStatement statement = conn.prepareStatement(SELECT_BY_KEY);
			statement.setInt(1, timeCd);
			
			//SQL文を実行し、Timeオブジェクトを取得する。
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				ret = new Time();
				ret.setTimeCd(resultSet.getInt("time_cd"));
				ret.setTimeName(resultSet.getString("time_name"));
			}
			
		} catch (SQLException e) {
			throw new DaoException();
		} finally {
			closeConnection();
		}
		
		return ret;
	}

}
