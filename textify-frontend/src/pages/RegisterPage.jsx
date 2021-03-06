import React from 'react';
import Input from '../components/Input';
import ButtonWithProgress from '../components/ButtonWithProgress';

export class RegisterPage extends React.Component {
  state = {
    nickname: '',
    username: '',
    password: '',
    passwordRepeat: '',
    pendingApiCall: false,
    errors: {},
    passwordRepeatConfirmed: true
  };

  onChangeNickname = (event) => {
    const value = event.target.value;
    const errors = { ...this.state.errors };
    delete errors.nickname;
    this.setState({ nickname: value, errors });
  };

  onChangeUsername = (event) => {
    const value = event.target.value;
    const errors = { ...this.state.errors };
    delete errors.username;
    this.setState({ username: value, errors });
  };

  onChangePassword = (event) => {
    const value = event.target.value;
    const passwordRepeatConfirmed = this.state.passwordRepeat === value;
    const errors = { ...this.state.errors };
    delete errors.password;
    errors.passwordRepeat = passwordRepeatConfirmed
      ? ''
      : 'Does not match to password';
    this.setState({ password: value, passwordRepeatConfirmed, errors });
  };

  onChangePasswordRepeat = (event) => {
    const value = event.target.value;
    const passwordRepeatConfirmed = this.state.password === value;
    const errors = { ...this.state.errors };
    errors.passwordRepeat = passwordRepeatConfirmed
      ? ''
      : 'Does not match to password';
    this.setState({ passwordRepeat: value, passwordRepeatConfirmed, errors });
  };

  onClickSignup = () => {
    const user = {
      username: this.state.username,
      Nickname: this.state.nickname,
      password: this.state.password
    };
    this.setState({ pendingApiCall: true });
    this.props.actions
      .postSignup(user)
      .then((response) => {
        this.setState({ pendingApiCall: false }, () =>
          this.props.history.push('/')
        );
      })
      .catch((apiError) => {
        let errors = { ...this.state.errors };
        if (apiError.response.data && apiError.response.data.validationErrors) {
          errors = { ...apiError.response.data.validationErrors };
        }
        this.setState({ pendingApiCall: false, errors });
      });
  };

  render() {
    return (
      <div className="container">
        <h1 className="text-center">Sign Up</h1>
        <div className="col-12 mb-3">
          <Input
            label="Nickname"
            placeholder="Your Nickname"
            value={this.state.nickname}
            onChange={this.onChangeNickname}
            hasError={this.state.errors.nickname && true}
            error={this.state.errors.nickname}
          />
        </div>
        <div className="col-12 mb-3">
          <Input
            label="Username"
            placeholder="Your username"
            value={this.state.username}
            onChange={this.onChangeUsername}
            hasError={this.state.errors.username && true}
            error={this.state.errors.username}
          />
        </div>
        <div className="col-12 mb-3">
          <Input
            label="Password"
            placeholder="Your password"
            type="password"
            value={this.state.password}
            onChange={this.onChangePassword}
            hasError={this.state.errors.password && true}
            error={this.state.errors.password}
          />
        </div>
        <div className="col-12 mb-3">
          <Input
            label="Password Repeat"
            placeholder="Repeat your password"
            type="password"
            value={this.state.passwordRepeat}
            onChange={this.onChangePasswordRepeat}
            hasError={this.state.errors.passwordRepeat && true}
            error={this.state.errors.passwordRepeat}
          />
        </div>
        <div className="text-center">
          <ButtonWithProgress
            onClick={this.onClickSignup}
            disabled={
              this.state.pendingApiCall || !this.state.passwordRepeatConfirmed
            }
            pendingApiCall={this.state.pendingApiCall}
            text="Sign Up"
          />
        </div>
      </div>
    );
  }
}

RegisterPage.defaultProps = {
  actions: {
    postSignup: () =>
      new Promise((resolve, reject) => {
        resolve({});
      })
  },
  history: {
    push: () => {}
  }
};

export default RegisterPage;