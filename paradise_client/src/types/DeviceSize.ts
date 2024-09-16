export enum DeviceSizeConstant {
	_4k = 0,
	Laptop_L = 1,
	Laptop_S = 2,
	Tablet = 3,
	Mobile_L = 4,
	Mobile_M = 5,
	Mobile_S = 6,
}

export const DeviceSize = {
	getDeviceSize: (): DeviceSizeConstant => {
		if (window.innerWidth >= 2560) {
			return DeviceSizeConstant._4k;
		}
		if (window.innerWidth >= 1440) {
			return DeviceSizeConstant.Laptop_L;
		}
		if (window.innerWidth >= 1024) {
			return DeviceSizeConstant.Laptop_S;
		}
		if (window.innerWidth >= 768) {
			return DeviceSizeConstant.Tablet;
		}
		if (window.innerWidth >= 425) {
			return DeviceSizeConstant.Mobile_L;
		}
		if (window.innerWidth >= 375) {
			return DeviceSizeConstant.Mobile_M;
		}
		return DeviceSizeConstant.Mobile_S;
	},
};
