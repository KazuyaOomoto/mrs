package jp.co.mgnc.business.reserve.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.co.mgnc.business.reserve.dto.Reserve;
import jp.co.mgnc.business.reserve.dto.ReserveDetail;
import jp.co.mgnc.business.common.dao.CommonDAO;
import jp.co.mgnc.business.common.exception.DaoException;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class ReserveDAO extends CommonDAO {

		//文間スペース
		private static final String SPACE = " ";

		//全会議室予約情報を取得するSQL
		private static final String SELECT = "select * from t_reserve" + SPACE;

		//会議室予約情報を登録するSQL
		private static final String INSERT = "insert into t_reserve values(?,?,?,?,?,?)";

		//会議室予約情報を削除するSQL
		private static final String DELETE = "delete from t_reserve" + SPACE;

		//指定従業員の会議室予約詳細情報を取得するSQL
		private static final String SELECT_DETAIL = "select reserve_date, time_cd, time_name, room_cd, room_name, emp_no, emp_name, department, tel, purpose from t_reserve" + SPACE;

		//条件用パラメータ
		private static final String JOIN_TIME_EXCLUDE_NULL = "inner join m_time using(time_cd)" + SPACE;
		private static final String JOIN_ROOM_EXCLUDE_NULL = "inner join m_room using(room_cd)" + SPACE;
		private static final String JOIN_EMP_EXCLUDE_NULL = "inner join m_employee using(emp_no)" + SPACE;
		private static final String BY_EMP_AFTER_DAY = "where  emp_no=? and reserve_date>=?" + SPACE;
		private static final String FOR_THE_PERIOD_OF = "where reserve_date between ? and ?" + SPACE;
		private static final String ONLY_ONE_RECORD = "where reserve_date=? and time_cd=? and room_cd=?" + SPACE;

		public ArrayList<Reserve> getReserveAllList() throws DaoException {

			//戻り値
			ArrayList<Reserve> ret = new ArrayList<>();

			try {
				//コネクション
				getConnection();

				//ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(SELECT);

				//データベースの問い合わせ
				ResultSet rs = pstmt.executeQuery();

				//結果を取得し、Reserveインスタンスに格納後、ArrayListにaddする
				while(rs.next()) {
					Reserve reserve = new Reserve();
					reserve.setReserveDate(rs.getDate("reserve_date").toLocalDate());
					reserve.setTimeCd(rs.getInt("time_cd"));
					reserve.setRoomCd(rs.getInt("room_cd"));
					reserve.setEmpNo(rs.getString("emp_no"));
					reserve.setTel(rs.getString("tel"));
					reserve.setPurpose(rs.getString("purpose"));

					//何件あるか不明のため、ArrayListで処理する
					ret.add(reserve);
				}
			} catch( SQLException e) {
				throw new DaoException();
			}finally {
				closeConnection();
			}

			return ret;
		}

		public Reserve selectReserveByKey(LocalDate localDate, int timeCd, int roomCd) throws DaoException {

			//戻り値
			Reserve ret = null;

			//会議室予約詳細情報SQLパラメータ
			final String[] SELECT_BY_KEY = {
					SELECT,
					ONLY_ONE_RECORD
			};

			try {
				//コネクション
				getConnection();

				//パラメータ配列をバッファに結合してSQL文を作成
				StringBuffer sqlbuf = new StringBuffer();
				for(String param : SELECT_BY_KEY) {
					sqlbuf.append(param);
				}

				//ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(sqlbuf.toString());

				//バッファを開放
				sqlbuf.setLength(0);

				//プレースホルダに値をセット
				pstmt.setDate(1,  Date.valueOf(localDate));
				pstmt.setInt(2,  timeCd);
				pstmt.setInt(3,  roomCd);

				//データベースの問い合わせ
				ResultSet rs = pstmt.executeQuery();

				//結果を取得し、Reserveインスタンスに格納後、ArrayListにaddする
				if(rs.next()) {
					ret = new Reserve();
					ret.setReserveDate(rs.getDate("reserve_date").toLocalDate());
					ret.setTimeCd(rs.getInt("time_cd"));
					ret.setRoomCd(rs.getInt("room_cd"));
					ret.setEmpNo(rs.getString("emp_no"));
					ret.setTel(rs.getString("tel"));
					ret.setPurpose(rs.getString("purpose"));
				}
			} catch( SQLException e) {
				throw new DaoException();
			}finally {
				closeConnection();
			}

			return ret;
		}

		public ReserveDetail selectReserveDetailByKey(LocalDate localDate, int timeCd, int roomCd) throws DaoException {

			//戻り値
			ReserveDetail ret = null;

			//会議室予約詳細情報SQLパラメータ
			final String[] SELECT_BY_KEY = {
					SELECT_DETAIL,
					JOIN_TIME_EXCLUDE_NULL,
					JOIN_ROOM_EXCLUDE_NULL,
					JOIN_EMP_EXCLUDE_NULL,
					ONLY_ONE_RECORD
			};

			try {
				//コネクション
				getConnection();

				//パラメータ配列をバッファに結合してSQL文を作成
				StringBuffer sqlbuf = new StringBuffer();
				for(String param : SELECT_BY_KEY) {
					sqlbuf.append(param);
				}

				//ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(sqlbuf.toString());

				//バッファを開放
				sqlbuf.setLength(0);

				//プレースホルダに値をセット
				pstmt.setDate(1,  Date.valueOf(localDate));
				pstmt.setInt(2,  timeCd);
				pstmt.setInt(3,  roomCd);

				//データベースの問い合わせ
				ResultSet rs = pstmt.executeQuery();

				//結果を取得し、Reserveインスタンスに格納後、ArrayListにaddする
				if(rs.next()) {
					ret = new ReserveDetail();
					ret.setReserveDate(rs.getDate("reserve_date").toLocalDate());
					ret.setTimeCd(rs.getInt("time_cd"));
					ret.setTimeName(rs.getString("time_name"));
					ret.setRoomCd(rs.getInt("room_cd"));
					ret.setRoomName(rs.getString("room_name"));
					ret.setEmpNo(rs.getString("emp_no"));
					ret.setEmpName(rs.getString("emp_name"));
					ret.setDepartment(rs.getString("department"));
					ret.setTel(rs.getString("tel"));
					ret.setPurpose(rs.getString("purpose"));
				}
			} catch( SQLException e) {
				throw new DaoException();
			}finally {
				closeConnection();
			}

			return ret;
		}

		public ArrayList<ReserveDetail> selectReserveDetailInAMonth(int year, int month) throws DaoException {

			//その月範囲で結果を絞り込む
			final String[] SELECT_DETAIL_IN_A_MONTH =
			{
					SELECT_DETAIL,
					JOIN_TIME_EXCLUDE_NULL,
					JOIN_ROOM_EXCLUDE_NULL,
					JOIN_EMP_EXCLUDE_NULL,
					FOR_THE_PERIOD_OF,
					"order by reserve_date, time_cd, room_cd",
			};

			//該当年月の初日と末日を算出
			LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
			LocalDate lastDayOfMonth = startDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

			ArrayList<ReserveDetail> ret = new ArrayList<>();

				try {
					//コネクション
					getConnection();

					//パラメータ配列をバッファに結合してSQL文を作成
					StringBuffer sqlbuf = new StringBuffer();
					for(String param : SELECT_DETAIL_IN_A_MONTH) {
						sqlbuf.append(param);
					}

					//ステートメントの作成
					PreparedStatement pstmt = conn.prepareStatement(sqlbuf.toString());

					//バッファを開放
					sqlbuf.setLength(0);

					//プレースホルダに値をセット
					pstmt.setDate(1,  Date.valueOf(startDayOfMonth));
					pstmt.setDate(2,  Date.valueOf(lastDayOfMonth));

					//データベースの問い合わせ
					ResultSet rs = pstmt.executeQuery();

					//結果を取得し、Reserveインスタンスに格納後、ArrayListにaddする
					while(rs.next()) {
						ReserveDetail rd = new ReserveDetail();
						rd.setReserveDate(rs.getDate("reserve_date").toLocalDate());
						rd.setTimeCd(rs.getInt("time_cd"));
						rd.setTimeName(rs.getString("time_name"));
						rd.setRoomCd(rs.getInt("room_cd"));
						rd.setRoomName(rs.getString("room_name"));
						rd.setEmpNo(rs.getString("emp_no"));
						rd.setEmpName(rs.getString("emp_name"));
						rd.setDepartment(rs.getString("department"));
						rd.setTel(rs.getString("tel"));
						rd.setPurpose(rs.getString("purpose"));
						ret.add(rd);
					}
				} catch( SQLException e) {
					throw new DaoException();
				}finally {
					closeConnection();
				}

			return ret;
		}

		public Map<Integer, ArrayList<ReserveDetail>> selectReserveDetailCanlender(int year, int month) throws DaoException {

			//その月のカレンダーを自動生成し、予約席詳細情報と外部結合を実施する
			final String[] SELECT_DETAIL_IN_A_MONTH_ALL =
			{
					"select ",
					"c.reserve_date, ",
					"r.time_cd, ",
					"r.time_name, ",
					"r.room_cd, ",
					"r.room_name, ",
					"r.emp_no, ",
					"r.emp_name, ",
					"r.department, ",
					"r.tel, ",
					"r.purpose ",
					"from ( ",
					"select ",
					"(date (?) + cast( cast (dates as varchar) || ' days' as interval ) )::date as reserve_date ",	//1
					"from ",
					"generate_series(0, (date (?) - date (?))::integer) \"MONTH\"(dates) ",	//2,3
					") as c ",
					"left outer join ( ",
					SELECT_DETAIL,
					JOIN_TIME_EXCLUDE_NULL,
					JOIN_ROOM_EXCLUDE_NULL,
					JOIN_EMP_EXCLUDE_NULL,
					FOR_THE_PERIOD_OF,						//"where reserve_date between ? and ?" 4,5
					") r ",
					"on c.reserve_date = r.reserve_date ",
					"order by c.reserve_date, r.time_cd, r.room_cd",
			};

			//該当年月の初日と末日を算出
			LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
			LocalDate lastDayOfMonth = startDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

			Map<Integer,ArrayList<ReserveDetail>>  ret = new HashMap<>(startDayOfMonth.lengthOfMonth());

				try {
					//コネクション
					getConnection();

					//パラメータ配列をバッファに結合してSQL文を作成
					StringBuffer sqlbuf = new StringBuffer();
					for(String param : SELECT_DETAIL_IN_A_MONTH_ALL) {
						sqlbuf.append(param);
					}

					//ステートメントの作成
					PreparedStatement pstmt = conn.prepareStatement(sqlbuf.toString());

					//バッファを開放
					sqlbuf.setLength(0);

					//プレースホルダに値をセット
					pstmt.setDate(1,  Date.valueOf(startDayOfMonth));
					pstmt.setDate(3,  Date.valueOf(startDayOfMonth));
					pstmt.setDate(4,  Date.valueOf(startDayOfMonth));
					pstmt.setDate(2,  Date.valueOf(lastDayOfMonth));
					pstmt.setDate(5,  Date.valueOf(lastDayOfMonth));

					//データベースの問い合わせ
					ResultSet rs = pstmt.executeQuery();

					//結果の取得数分以下の処理を実行
					while(rs.next()) {
						ReserveDetail rd = new ReserveDetail();
						rd.setReserveDate(rs.getDate("reserve_date").toLocalDate());
						rd.setTimeCd(rs.getInt("time_cd"));
						rd.setTimeName(rs.getString("time_name"));
						rd.setRoomCd(rs.getInt("room_cd"));
						rd.setRoomName(rs.getString("room_name"));
						rd.setEmpNo(rs.getString("emp_no"));
						rd.setEmpName(rs.getString("emp_name"));
						rd.setDepartment(rs.getString("department"));
						rd.setTel(rs.getString("tel"));
						rd.setPurpose(rs.getString("purpose"));

						//0から始まるキー値とする
						int key = rd.getReserveDate().getDayOfMonth();

						//指定のキーでリストが生成されているか確認
						//リストが未生成
						//指定キーに該当するリストを作成
						ret.putIfAbsent(key, new ArrayList<ReserveDetail>());

						//!(取得結果の従業員Noがnull =　SQLの構文よりその日の会議は一つもない)
						//=その日の予約があった
						if(rd.getEmpNo() != null) {
							ret.get(key).add(rd);
						}
					}
				} catch( SQLException e) {
					throw new DaoException();
				}finally {
					closeConnection();
				}

			return ret;
		}

		public ArrayList<ReserveDetail> selectReserveDetailByEmpAfterDay(String empNo, LocalDate localDate ) throws DaoException {

			//戻り値
			ArrayList<ReserveDetail> ret = new ArrayList<>();

			//会議室予約詳細情報SQLパラメータ
			final String[] SELECT_DETAIL_EMP_AFTER_DAY = {
					SELECT_DETAIL,
					JOIN_TIME_EXCLUDE_NULL,
					JOIN_ROOM_EXCLUDE_NULL,
					JOIN_EMP_EXCLUDE_NULL,
					BY_EMP_AFTER_DAY,
					"order by reserve_date, time_cd, room_cd"
			};

			try {
				//コネクション
				getConnection();

				//パラメータ配列をバッファに結合してSQL文を作成
				StringBuffer sqlbuf = new StringBuffer();
				for(String param : SELECT_DETAIL_EMP_AFTER_DAY) {
					sqlbuf.append(param);
				}

				//ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(sqlbuf.toString());

				//バッファを開放
				sqlbuf.setLength(0);

				//プレースホルダに値をセット
				pstmt.setString(1,  empNo);
				pstmt.setDate(2,  Date.valueOf(localDate));

				//データベースの問い合わせ
				ResultSet rs = pstmt.executeQuery();

				//結果を取得し、Reserveインスタンスに格納後、ArrayListにaddする
				while(rs.next()) {
					ReserveDetail rd = new ReserveDetail();
					rd.setReserveDate(rs.getDate("reserve_date").toLocalDate());
					rd.setTimeCd(rs.getInt("time_cd"));
					rd.setTimeName(rs.getString("time_name"));
					rd.setRoomCd(rs.getInt("room_cd"));
					rd.setRoomName(rs.getString("room_name"));
					rd.setPurpose(rs.getString("purpose"));
					rd.setTel(rs.getString("tel"));
					ret.add(rd);
				}
			} catch( SQLException e) {
				throw new DaoException();
			}finally {
				closeConnection();
			}

			return ret;
		}

		public int insert(Reserve reserve) throws DaoException {

			//戻り値
			int ret = 0;

			try {
				//コネクション
				getConnection();

				//ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(INSERT);
				pstmt.setDate(1,  Date.valueOf(reserve.getReserveDate()));
				pstmt.setInt(2,  reserve.getTimeCd());
				pstmt.setInt(3,  reserve.getRoomCd());
				pstmt.setString(4, reserve.getEmpNo());
				pstmt.setString(5, reserve.getTel());
				pstmt.setString(6, reserve.getPurpose());

				//データベースの更新を実行
				ret = pstmt.executeUpdate();

			} catch(SQLException e) {
				throw new DaoException();
			} finally {
				closeConnection();
			}

			return ret;
		}

		public int deleteReserveByKey(LocalDate localDate, int timeCd, int roomCd) throws DaoException {

			//戻り値
			int ret = 0;

			//会議室予約詳細情報SQLパラメータ
			final String[] DELETE_BY_KEY = {
					DELETE,
					ONLY_ONE_RECORD
			};

			try {
				//コネクション
				getConnection();

				//パラメータ配列をバッファに結合してSQL文を作成
				StringBuffer sqlbuf = new StringBuffer();
				for(String param : DELETE_BY_KEY) {
					sqlbuf.append(param);
				}

				//ステートメントの作成
				PreparedStatement pstmt = conn.prepareStatement(sqlbuf.toString());

				//バッファを開放
				sqlbuf.setLength(0);

				//プレースホルダに値をセット
				pstmt.setDate(1,  Date.valueOf(localDate));
				pstmt.setInt(2,  timeCd);
				pstmt.setInt(3,  roomCd);

				//データベースの問い合わせ
				ret = pstmt.executeUpdate();

			} catch( SQLException e) {
				throw new DaoException();
			}finally {
				closeConnection();
			}

			return ret;
		}

}
