const initialState = {
  id: 0,
  username: '',
  nickname: '',
  image: '',
  password: '',
  isLoggedIn: false
};

export default function authReducer(state = initialState, action) {
  if (action.type === 'logout-success') {
    return { ...initialState };
  } else if (action.type === 'login-success') {
    return {
      ...action.payload,
      isLoggedIn: true
    };
  } else if (action.type === 'update-success') {
    return {
      ...state,
      nickname: action.payload.nickname,
      image: action.payload.image
    };
  }
  return state;
}