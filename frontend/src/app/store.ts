import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import { pageStatusReducer } from './PageStatusSlice';

export const store = configureStore({
  reducer: {
    counter: pageStatusReducer,
  },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
