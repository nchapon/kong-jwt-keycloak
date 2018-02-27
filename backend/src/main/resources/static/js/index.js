// Axios Config
const HTTP= axios.create({
    baseURL:"http://localhost:8000/oidc-secure"
})


new Vue({
    el: '#app',

    data: {
        message: "",
        onError:false,
        onSuccess:false
    },
    methods: {
        callSecured: function(event){
            HTTP.get('secured',{withCredentials:true})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.message = response.data
                    this.onError=false
                    this.onSuccess=true
                })
                .catch(e => {
                    this.message = e.response.data
                    this.onError=true
                    this.onSuccess=false
                    console.log(e)
                })
        },
        callAdmin: function(event){
            HTTP.get('admin',{withCredentials:true})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.message = response.data
                    this.onError=false
                    this.onSuccess=true
                })
                .catch(e => {
                    this.message = e.response.data
                    this.onError=true
                    this.onSuccess=false
                    console.log(e)
                })
        }
    }
});
