import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import AddView from "./views/AddView";
import ListView from "./views/ListView";
import EditView from "./views/EditView";

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route path="/edit/:id" component={EditView} />
        <Route exact path="/" component={ListView} />
        <Route path="/add" component={AddView} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
