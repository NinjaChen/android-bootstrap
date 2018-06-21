package rocks.ninjachen.hbridgek.entity;

/**
 * Created by ninja on 1/6/17.
 */
public enum LoadingStatus {
    // 还没获取实时数据
    NOT_START,
    // 正在获取实时数据
    LOADING,
    // 已经获取实时数据
    LOADED,
    // 用于card的余额查询，查询失败
    LOAD_FAILED
}
