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
    axios.get("/api/clients").then((datos) => {
    this.clients = datos.data;
    });

    axios.get("/api/clients/current").then((datos) => {
      this.currentClient = datos.data;
      this.cards = this.currentClient.cards;
    });
  },

  methods: {
    createCard() {
      axios
        .post(
          "/api/clients/current/cards",
          `color=${this.cardColor}&type=${this.cardType}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then(function (response) {
          window.location.href = "/web/cards.html";
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