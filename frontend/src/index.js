import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import Keycloak from 'keycloak-js';
import axios from 'axios';





ReactDOM.render(<App />, document.getElementById("root"));


const kc = Keycloak('/keycloak.json');
kc.init({onLoad: 'login-required'}).success(authenticated => {
  if (authenticated) {
      //store.getState().keycloak = kc;
      console.log("TEST" + kc.token);

    ReactDOM.render(<App />, document.getElementById("root"));
  } else {
      console.log("Unable to login keycloak");

  }
});

axios.interceptors.request.use(config => {
    return refreshToken().then(() => {
        console.log("Bearer " + kc.token);
    config.headers.Authorization = 'Bearer ' + kc.token;
    return Promise.resolve(config)
  }).catch(() => {
    kc.login();
  })
});


//need to wrap the KC "promise object" into a real Promise object
const refreshToken = (minValidity = 5) => {
    return new Promise((resolve, reject) => {
        console.log("Bla Bla")
    kc.updateToken(minValidity)
      .success(() => resolve())
      .error(error => reject(error))
  });
};

registerServiceWorker();
