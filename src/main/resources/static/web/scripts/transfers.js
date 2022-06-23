Vue.createApp({
    data() {
    return {
        clients: [],
        ownAccount:'',
        originAccount:[],
        destinationAccount:[],
        amount:0,
        description:'',
        idCliente: 0,
        currentClient: [],
        accounts:[],
        };
    },

    created() {
    axios.get("/api/clients").then((datos) => {
    this.clients = datos.data;
    });

    axios.get("/api/clients/current")
    .then((datos) => {
      this.currentClient = datos.data;
      this.accounts = this.currentClient.accounts.filter(account => account.disable == false);
    });
  },

  methods: {
    createTransfer() {
      axios.post(
          "/api/transactions",
          `originAccount=${this.originAccount}&destinationAccount=${this.destinationAccount}&amount=${this.amount}&description=${this.description}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then(function (response) {
          window.location.href = "/web/accounts.html";
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    logOut(){
        axios.post('/api/logout')
            .then(function(response){
            window.location.href = "/web/index.html";
            })
    },
  },

  computed: {},
}).mount("#app");