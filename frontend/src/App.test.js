import { render, screen } from '@testing-library/react';
import App from './App';
import React from 'react';

describe('App', () => {
  test('renders Login page when not logged in', () => {
    render(<App />);
    expect(screen.getByRole('heading', { name: /login/i })).toBeInTheDocument();
  });

  test('renders main dashboard when logged in', () => {
    // Directly override the token state by wrapping App and forcing the first useState
    const setToken = jest.fn();
    jest.spyOn(React, 'useState')
      .mockReturnValueOnce(['fake-token', setToken])  // token state → truthy
      .mockReturnValueOnce([null, jest.fn()])         // disputeTx
      .mockReturnValueOnce([0, jest.fn()]);           // activeTab

    render(<App />);

    expect(screen.getByRole('heading', { name: 'Transactions Dispute Portal' })).toBeInTheDocument();
    expect(screen.getByText('Transactions')).toBeInTheDocument();
    expect(screen.getByText('Dispute History')).toBeInTheDocument();

    jest.restoreAllMocks();
  });
});