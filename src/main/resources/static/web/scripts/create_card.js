Vue.createApp({
    data() {
    return {
        clients: [],
        currentClient: [],
        accounts: [],
        loans: [],
        cards: [],
        cardType: "",
        cardColor: "",
        };
    },

    created() {
    axios.get("http://localhost:8080/api/clients").then((datos) => {
    this.clients = datos.data;
    });

    axios.get("http://localhost:8080/api/clients/current").then((datos) => {
      this.currentClient = datos.data;
      this.cards = this.currentClient.cards;
    });
  },

  methods: {
    createCard() {
      axios
        .post(
          "http://localhost:8080/api/clients/current/cards",
          `color=${this.cardColor}&type=${this.cardType}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then(function (response) {
          window.location.href = "http://localhost:8080/web/cards.html";
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    logOut(){
      axios.post('/api/logout')
          .then(function(response){
          window.location.href = "http://localhost:8080/web/index.html";
          })
  },
  },

  computed: {},
}).mount("#app");