
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import AppView from './AppView';

test('checks for rendering AppView', () => {
	const { container } = render(<AppView/>);
	const appViewElement = container.querySelector('main.AppView')
	expect(appViewElement).toBeInTheDocument();
});
	