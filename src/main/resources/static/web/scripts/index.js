Vue.createApp({

    data() {
        return {
                clientFirstName:"",
                clientLastName:"",
                newClientMail:"",
                newClientPassword:"",
                clientMail:"",
                clientPassword:"",
        }
    },

    created() {  
    },

    methods: {

        logIn(){
            axios.post('/api/login',
            `mail=${this.clientMail}&password=${this.clientPassword}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}
                })
            .then(function(response){
                window.location.href = "http://localhost:8080/web/accounts.html";
            })
            .catch(function(error){
                console.log(error)
            });
        },

        signUp(){
            axios.post('/api/clients',
            `nombre=${this.clientFirstName}&apellido=${this.clientLastName}&mail=${this.newClientMail}&password=${this.newClientPassword}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}
                })
            .then(response =>{
                axios.post('/api/login',
                `mail=${this.newClientMail}&password=${this.newClientPassword}`,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}
                    })
                .then(function(response){
                    window.location.href = "http://localhost:8080/web/accounts.html";
                })

            })
            .catch(function(error){
                console.log(error)
            });
        },

},

    
    computed: {


},


}).mount('#app')