import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';


// const HTTP= axios.create({
//     baseURL:"http://localhost:8000/jwt-secure"
// })



class App extends Component {

    handleClick(e){
        console.log("Secured click");

        axios.get('http://localhost:8000/jwt-secure/secured')
                .then(response => {
                    // JSON responses are automatically parsed.
                    console.log(response.data)
                })
                .catch(e => {
                    console.log(e.toString())
                })

    }

    handleClickAdminOnly(e){
        console.log("Admin Only click");
    }


  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>


            <button onClick={this.handleClick}>
                  Secured
            </button>

            <button onClick={this.handleClickAdminOnly}>
                  Admin Only
            </button>

      </div>
    );
  }
}

export default App;
