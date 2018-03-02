import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';


// const HTTP= axios.create({
//     baseURL:"http://localhost:8000/jwt-secure"
// })



class App extends Component {


    constructor(props){
        super(props)
        this.state={message:"No message"}
        this.handleClick = this.handleClick.bind(this);
        this.handleClickAdminOnly = this.handleClickAdminOnly.bind(this);

    }

    handleClick(e){
        console.log("Secured click");

        axios.get('http://localhost:8000/jwt-secure/secured')
                .then(response => {
                    // JSON responses are automatically parsed.
                    console.log(response.data)
                    this.setState({
                        message:response.data.message
                    })

                })
                .catch(e => {
                    console.log(e.toString())
                })

    }

    handleClickAdminOnly(e){
        axios.get('http://localhost:8000/jwt-secure/admin')
                .then(response => {
                    // JSON responses are automatically parsed.
                    console.log(response.data)
                    this.setState({
                        message:response.data.message
                    })

                })
                .catch(e => {

                    this.setState({
                        message:e.toString()
                    })
                })
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

            <p>Welcome {this.state.message}</p>

      </div>
    );
  }
}

export default App;
