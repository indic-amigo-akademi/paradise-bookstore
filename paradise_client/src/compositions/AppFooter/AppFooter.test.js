
import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import AppFooter from './AppFooter';

test('checks for rendering AppFooter', () => {
	const { container } = render(<AppFooter/>);
	const appFooterElement = container.querySelector('footer.AppFooter')
	expect(appFooterElement).toBeInTheDocument();
});
	