import { render, waitFor } from "@testing-library/react";
import ListView from "./ListView";
import { MemoryRouter } from "react-router-dom";

describe("ListView Component", () => {
  it("should render a non-empty list", async () => {
    const { getByTestId } = render(
      <MemoryRouter>
        <ListView />
      </MemoryRouter>
    );

    await waitFor(() => {
      // Check if the list (identified by a testID) is not empty
      const list = getByTestId("people-list");
      expect(list.children.length).toBeGreaterThan(0);
    });
  });
});
