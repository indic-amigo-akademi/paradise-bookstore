
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import AppHeader from './AppHeader';

test('checks for rendering AppHeader', () => {
	const { container } = render(<AppHeader/>);
	const appHeaderElement = container.querySelector('header.AppHeader')
	expect(appHeaderElement).toBeInTheDocument();
});
	